package cn.andylhl.crowd;

import cn.andylhl.crowd.constant.AccessPassResources;
import org.junit.Test;

/***
 * @Title: TestString
 * @Description:
 * @author: lhl
 * @date: 2021/1/17 9:03
 */
public class TestString {

    @Test
    public void testSplit() {
        String s = "/";
        String[] splits = s.split("/");
        System.out.println(splits.length);
        for (String s1 : splits) {
            System.out.println("-->" + s1 + "<--");
        }
    }

    @Test
    public void testJudge() {
        String s = "/12/111member/to/reg/page";
        Boolean tag = AccessPassResources.judgeCurrentServletPathWhetherStaticResource(s);
        System.out.println(tag);
    }
}
