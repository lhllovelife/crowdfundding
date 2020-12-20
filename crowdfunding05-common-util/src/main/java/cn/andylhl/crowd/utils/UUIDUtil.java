package cn.andylhl.crowd.utils;

import java.util.UUID;

/***
 * @Title: UUIDUtil
 * @Description: 获取32位随机字符串
 * @author: lhl
 * @date: 2020/12/20 16:26
 */
public class UUIDUtil {

    private UUIDUtil(){
    }

    /**
     * 获取一个UUID随机字符串
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
