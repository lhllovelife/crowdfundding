package cn.andylhl.crowd;

import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import org.junit.Test;

/***
 * @Title: TestSendMessageUtil
 * @Description:
 * @author: lhl
 * @date: 2021/1/15 15:49
 */
public class TestSendMessageUtil {

    @Test
    public void testRandom() {

        for ( int i = 1; i <= 10; i++) {

            Double a = Math.random() * 10;
            int b = (int) Math.round(a) ;
            System.out.println(a + "  " + b);
        }
    }

    @Test
    public void testSendMessage() {

        for ( int i = 1; i <= 100; i++) {

            ResultEntity<String> resultEntity = CrowdUtil.sendMessage();
            System.out.println(resultEntity);
        }
    }
}
