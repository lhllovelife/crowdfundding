package cn.andylhl.crowd.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * @Title: DateUtil
 * @Description: 日期格式化工具类
 * @author: lhl
 * @date: 2020/12/20 16:24
 */
public class DateUtil {

    private DateUtil(){}

    /**
     *  日期格式化
     * @param date 当前日期
     * @param pattern 格式
     * @return 返回当前经过格式化的日期
     */
    public static String format(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }
}
