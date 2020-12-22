package cn.andylhl.crowd.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * @Title: TestSSMController
 * @Description: 测试环境搭建controller
 * @author: lhl
 * @date: 2020/12/22 9:15
 */

@Controller
public class TestSSMController {

    @RequestMapping("/test/ssm.do")
    public @ResponseBody Object testSSM(){
        return "success : true";
    }
}
