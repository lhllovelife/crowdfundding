package cn.andylhl.crowd.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * @Title: PortalController
 * @Description: 显示首页
 * @author: lhl
 * @date: 2021/1/14 16:52
 */

@Controller
public class PortalController {

    private Logger logger = LoggerFactory.getLogger(PortalController.class);

    @RequestMapping("/")
    public String showPortalPage() {
        logger.info("authentication-consumer服务，访问门户主页");

        // 省略加载主页面数据
        return "portal";
    }

}
