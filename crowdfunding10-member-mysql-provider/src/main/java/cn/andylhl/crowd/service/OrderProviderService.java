package cn.andylhl.crowd.service;

import cn.andylhl.crowd.vo.AddressVO;
import cn.andylhl.crowd.vo.OrderProjectVO;
import cn.andylhl.crowd.vo.OrderVO;

import java.util.List;

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

    /**
     * 保存地址信息
     * @param addressVO
     */
    void saveAddress(AddressVO addressVO);

    /**
     * 根据memberId查询地址信息
     * @param memberId
     * @return
     */
    List<AddressVO> getAddressList(String memberId);

    /**
     * 保存订单数据
     * @param orderVO
     */
    void saveOrderVO(OrderVO orderVO);
}
