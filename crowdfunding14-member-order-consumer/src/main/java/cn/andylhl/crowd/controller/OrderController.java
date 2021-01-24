package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.MySQLRemoteService;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.utils.UUIDUtil;
import cn.andylhl.crowd.vo.AddressVO;
import cn.andylhl.crowd.vo.OrderProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

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
        logger.info("crowd-order服务, 获取订单项目信息");

        ResultEntity<OrderProjectVO> resultEntity =  mySQLRemoteService.getOrderProjectVORemote(projectId, returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            OrderProjectVO orderProjectVO = resultEntity.getData();
            // 将订单项目数据放到session域对象中
            session.setAttribute("orderProjectVO", orderProjectVO);
        }
        return "confirm-return";
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String confimOrder(@PathVariable("returnCount") Integer returnCount, HttpSession session ) {
        logger.info("crowd-order服务, 跳转到确认订单页面");
        // 1. 从session取出orderProjectVO对象
        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");
        // 2. returnCount
        orderProjectVO.setReturnCount(returnCount);
        // 3. 将该对象重新设置到session域对象中
        session.setAttribute("orderProjectVO", orderProjectVO);
        // 跳转到回报页面
        return "confirm-order";
    }

    @RequestMapping("/save/address/info")
    public @ResponseBody ResultEntity<String> saveAddress(AddressVO addressVO) {
        logger.info("crowd-order服务, 保存地址信息");
        // 生成uuid主键
        addressVO.setId(UUIDUtil.getUUID());
        // 调用mysql-provider服务保存
        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);
        return resultEntity;
    }

    @RequestMapping("/get/address/list/by/memberid")
    public @ResponseBody ResultEntity<List<AddressVO>> getAddressListById(@RequestParam("memberId") String memberId) {
        logger.info("crowd-order服务, 根据memberId查询地址信息");

        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressListRemote(memberId);

        return resultEntity;
    }

}
