package cn.andylhl.crowd.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

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

}
