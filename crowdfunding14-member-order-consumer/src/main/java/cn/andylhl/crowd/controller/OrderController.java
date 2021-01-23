package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.MySQLRemoteService;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.OrderProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/***
 * @Title: OrderController
 * @Description: 订单处理控制器
 * @author: lhl
 * @date: 2021/1/23 17:40
 */

@Controller
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") String projectId,
                                        @PathVariable("returnId") String returnId,
                                        HttpSession session){
        logger.info("crowd-project服务, 获取订单项目信息");

        ResultEntity<OrderProjectVO> resultEntity =  mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            OrderProjectVO orderProjectVO = resultEntity.getData();
            // 将订单项目数据放到session域对象中
            session.setAttribute("orderProjectVO", orderProjectVO);
        }
        return "confirm-return";
    }
}
