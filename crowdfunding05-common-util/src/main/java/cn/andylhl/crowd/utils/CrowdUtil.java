package cn.andylhl.crowd.utils;

import javax.servlet.http.HttpServletRequest;

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
}
