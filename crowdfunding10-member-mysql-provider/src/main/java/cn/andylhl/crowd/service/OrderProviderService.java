package cn.andylhl.crowd.service;

import cn.andylhl.crowd.vo.OrderProjectVO;

/***
 * @Title: OrderProviderService
 * @Description:
 * @author: lhl
 * @date: 2021/1/23 18:38
 */
public interface OrderProviderService {

    /**
     * 获取项目订单信息
     * @param projectId
     * @param returnId
     * @return
     */
    OrderProjectVO getOrderProjectVO(String projectId, String returnId);
}
