package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.service.OrderProviderService;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.OrderProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Title: OrderProviderController
 * @Description: 订单相关数据服务
 * @author: lhl
 * @date: 2021/1/23 18:35
 */

@RestController
public class OrderProviderController {

    Logger logger = LoggerFactory.getLogger(ProjectProviderController.class);

    @Autowired
    private OrderProviderService orderProviderService;

    /**
     * 获取项目订单信息
     * @param projectId
     * @param returnId
     * @return
     */
    @RequestMapping("/get/order/project/vo/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") String projectId, @RequestParam("returnId") String returnId) {
        logger.info("mysql-provider服务，获取项目订单信息");

        try {
            OrderProjectVO orderProjectVO = orderProviderService.getOrderProjectVO(projectId, returnId);
            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
