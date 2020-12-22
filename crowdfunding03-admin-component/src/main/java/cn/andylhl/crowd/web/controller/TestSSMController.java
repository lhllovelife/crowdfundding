package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.entity.TestStudent;
import cn.andylhl.crowd.utils.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/***
 * @Title: TestSSMController
 * @Description: 测试环境搭建controller
 * @author: lhl
 * @date: 2020/12/22 9:15
 */

@Controller
public class TestSSMController {

    private Logger logger = LoggerFactory.getLogger(TestSSMController.class);

    @RequestMapping("/test/ssm.do")
    public @ResponseBody Object testSSM(HttpServletRequest request){
        logger.info("访问测试环境方法");
        System.out.println(CrowdUtil.judgeRequestType(request));
        return "success : true";
    }

    @RequestMapping("test/ajax1.do")
    public @ResponseBody Object testAjax1(@RequestParam("id") String id,
                                          @RequestParam("name") String name,
                                          HttpServletRequest request){
        System.out.println(CrowdUtil.judgeRequestType(request));
        String s = null;
        System.out.println(s.length());
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        logger.info(id);
        logger.info(name);
        return map;
    }

    @RequestMapping("test/ajax2.do")
    public @ResponseBody Object testAjax2(@RequestBody TestStudent student, HttpServletRequest request){
        System.out.println(CrowdUtil.judgeRequestType(request));
        logger.info(student.toString());
        return student;
    }

}
