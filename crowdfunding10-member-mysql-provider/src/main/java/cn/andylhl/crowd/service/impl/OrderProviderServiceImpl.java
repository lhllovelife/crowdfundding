package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.mapper.OrderProjectPOMapper;
import cn.andylhl.crowd.mapper.ProjectPOMapper;
import cn.andylhl.crowd.po.OrderProjectPO;
import cn.andylhl.crowd.service.OrderProviderService;
import cn.andylhl.crowd.vo.OrderProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Title: OrderProviderServiceImpl
 * @Description:
 * @author: lhl
 * @date: 2021/1/23 18:38
 */

@Service
public class OrderProviderServiceImpl implements OrderProviderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;



    @Override
    public OrderProjectVO getOrderProjectVO(String projectId, String returnId) {


        // 1. 根据项目returnId首先获取项目信息有关内容
        OrderProjectVO orderProjectVO = orderProjectPOMapper.getOrderProjectVO(returnId);
        // 2.如果该回报产品限制数量, 根据returnId查询该回报已经有多少支持
        if (orderProjectVO.getCount() != 0) {
            Integer returnSupportCount = projectPOMapper.getReturnSupportCount(returnId);
            Integer remianCount = orderProjectVO.getCount() - returnSupportCount;
            // 将该回报产品剩余数目设置到orderProjectVO中
            orderProjectVO.setRemainCount(remianCount);
        }
        return orderProjectVO;
    }
}
