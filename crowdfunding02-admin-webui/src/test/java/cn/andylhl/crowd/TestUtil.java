package cn.andylhl.crowd;

import cn.andylhl.crowd.utils.MD5Util;
import org.junit.Test;

/***
 * @Title: TestUtil
 * @Description: 测试工具类的方法
 * @author: lhl
 * @date: 2020/12/23 19:17
 */
public class TestUtil {


    @Test
    public void testMD5() {
        String source = "123";
        String target = MD5Util.getMD5(source);
        System.out.println(target);
    }
}
