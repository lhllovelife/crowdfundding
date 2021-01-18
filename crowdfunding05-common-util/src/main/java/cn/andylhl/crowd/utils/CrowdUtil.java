package cn.andylhl.crowd.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/***
 * @Title: CrowdUtil
 * @Description: 工具类
 * @author: lhl
 * @date: 2020/12/22 15:03
 */
public class CrowdUtil {

    private CrowdUtil (){}

    /**
     * 判断当前请求是否为AJAX请求
     * @return
     * true: 是ajax请求
     * false: 不是ajax请求
     */
    public static Boolean judgeRequestType(HttpServletRequest request){
        // application/json, text/javascript, */*; q=0.01
        // XMLHttpRequest
        //1. 获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestedHeader = request.getHeader("X-Requested-With");
        return (acceptHeader != null && acceptHeader.contains("application/json")) || (xRequestedHeader != null && xRequestedHeader.equals("XMLHttpRequest"));
    }


    /**
     * 随机生成一个四位数字的验证码，以短信形式发送给用户（模拟）
     * @return
     *  如果发送成功则返回的结果是，发送的验证码
     *  发送失败，则返回错误消息
     */
    public static ResultEntity<String> sendMessage() {

        StringBuilder verifyCode = new StringBuilder();

        for (int i = 0; i < 4 ; i++) {
            int num = (int) Math.floor(Math.random() * 10);
            verifyCode.append(num);
        }
        if (verifyCode.toString().length() != 4) {
            return ResultEntity.failed("验证码发送失败");
        }
        return ResultEntity.successWithData(verifyCode.toString());
    }

    /**
     *
     * @param sendMailAddress 发送方邮箱地址
     * @param receiveMailAddress 接受方邮箱地址
     * @param user 发送邮件方的QQ号 ("2432707158")
     * @param password POP3/SMTP协议授权码 ("ajlzgiopwcnheaie")
     * @return
     * @throws Exception
     */
    public static ResultEntity<String> sendVerifyCodeByEmail(String sendMailAddress, String receiveMailAddress, String user, String password) throws Exception {

        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "false");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", "smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");
        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);
        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，POP3/SMTP协议授权码 163使用：smtp.163.com
        ts.connect("smtp.qq.com", user, password);
        // 创建邮件

        StringBuilder verifyCode = new StringBuilder();

        for (int i = 0; i < 4 ; i++) {
            int num = (int) Math.floor(Math.random() * 10);
            verifyCode.append(num);
        }
        Message message = createSimpleMail(session, sendMailAddress, receiveMailAddress, verifyCode.toString());
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();

        return ResultEntity.successWithData(verifyCode.toString());
    }

    /**
     * @Method: createSimpleMail
     * @Description: 创建一封只包含文本的邮件
     */
    public static MimeMessage createSimpleMail(Session session, String sendMailAddress, String receiveMailAddress, String verifyCode) throws Exception {

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(sendMailAddress));
        // 指明邮件的收件人，发件人和收件人如果是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailAddress));
        // 邮件的标题
        message.setSubject("LHL众筹");
        // 邮件的文本内容
        message.setContent("欢迎您注册【LHL众筹平台】,账号注册验证码为(两分钟有效):"+verifyCode+",请勿回复此邮箱", "text/html;charset=UTF-8");

        // 返回创建好的邮件对象
        return message;
    }

    /**
     * 专门负责上传文件到 OSS 服务器的工具方法
     * @param endpoint OSS 参数
     * @param accessKeyId OSS 参数
     * @param accessKeySecret OSS 参数
     * @param inputStream 要上传的文件的输入流
     * @param bucketName OSS 参数
     * @param bucketDomain OSS 参数
     * @param originalName 要上传的文件的原始文件名
     * @return 包含上传结果以及上传的文件在 OSS 上的访问路径
     */
    public static ResultEntity<String> uploadFileToOss(String endpoint, String accessKeyId, String accessKeySecret, InputStream inputStream, String bucketName, String bucketDomain, String originalName) {

        // 创建 OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 生成上传文件的目录
        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成上传文件在 OSS 服务器上保存时的文件名
        // 原始文件名：beautfulgirl.jpg
        // 生成文件名：wer234234efwer235346457dfswet346235.jpg
        // 使用 UUID 生成文件主体名称
        String fileMainName = UUID.randomUUID().toString().replace("-", "");
        // 从原始文件名中获取文件扩展名
        String extensionName = originalName.substring(originalName.lastIndexOf("."));
        // 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
        String objectName = folderName + "/" + fileMainName + extensionName;
        try {
            // 调用 OSS 客户端对象的方法上传文件并获取响应结果数据
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            // 从响应结果中获取具体响应消息
            ResponseMessage responseMessage = putObjectResult.getResponse();
            // 根据响应状态码判断请求是否成功
            if(responseMessage == null) {
                // 拼接访问刚刚上传的文件的路径
                String ossFileAccessPath = bucketDomain + "/" + objectName;
                // 当前方法返回成功
                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();
                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();
                // 当前方法返回失败
                return ResultEntity.failed(" 当 前 响 应 状 态 码 ="+statusCode+" 错 误 消 息 ="+errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 当前方法返回失败
            return ResultEntity.failed(e.getMessage());
        } finally {
            if(ossClient != null) {
                // 关闭 OSSClient。
                ossClient.shutdown();
            }
        }
    }
}
