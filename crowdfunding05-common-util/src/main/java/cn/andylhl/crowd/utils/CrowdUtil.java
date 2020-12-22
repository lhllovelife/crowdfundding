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
}
