package cn.andylhl.crowd;

import cn.andylhl.crowd.constant.AccessPassResources;
import org.junit.Test;

import java.time.LocalDate;

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

    @Test
    public void testTime() {
        // 开始日期
        LocalDate startDate = LocalDate.parse("2021-01-05");
        System.out.println(startDate);
        // 结束日期
        LocalDate endDate = LocalDate.now();
        System.out.println(endDate);
        // 剩余天数
        int lastDayInt = (int) (endDate.toEpochDay() - startDate.toEpochDay());

        System.out.println(lastDayInt);
    }
}
