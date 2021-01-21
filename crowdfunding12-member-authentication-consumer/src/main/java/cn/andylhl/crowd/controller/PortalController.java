package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.MySQLRemoteService;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.PortalTypeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/***
 * @Title: PortalController
 * @Description: 显示首页
 * @author: lhl
 * @date: 2021/1/14 16:52
 */

@Controller
public class PortalController {

    private Logger logger = LoggerFactory.getLogger(PortalController.class);

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/")
    public String showPortalPage(Model model) {
        logger.info("authentication-consumer服务，访问门户主页");

        // 1. 调用远程服务获取首页要显示的数据
        ResultEntity<List<PortalTypeVO>> resultEntity = mySQLRemoteService.getPrtalTypeProjectDataRemote();

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            // 将查询结果数据封装到request域
            List<PortalTypeVO> portalTypeVOList = resultEntity.getData();
            model.addAttribute(Constant.ATTR_NAME_PORTAL_DATA, portalTypeVOList);
        }

        // 省略加载主页面数据
        return "portal";
    }

}
