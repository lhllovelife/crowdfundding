package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.mapper.AddressPOMapper;
import cn.andylhl.crowd.mapper.OrderProjectPOMapper;
import cn.andylhl.crowd.mapper.ProjectPOMapper;
import cn.andylhl.crowd.po.AddressPO;
import cn.andylhl.crowd.po.OrderProjectPO;
import cn.andylhl.crowd.service.OrderProviderService;
import cn.andylhl.crowd.vo.AddressVO;
import cn.andylhl.crowd.vo.OrderProjectVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 * @Title: OrderProviderServiceImpl
 * @Description:
 * @author: lhl
 * @date: 2021/1/23 18:38
 */

@Transactional
@Service
public class OrderProviderServiceImpl implements OrderProviderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;


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

    /**
     * 保存地址信息
     * @param addressVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
    @Override
    public void saveAddress(AddressVO addressVO) {
        // 创建一个PO对象
        AddressPO addressPO = new AddressPO();
        BeanUtils.copyProperties(addressVO, addressPO);
        // 执行保存
        addressPOMapper.insertSelective(addressPO);
    }

    /**
     * 根据memberId查询地址信息
     * @param memberId
     * @return
     */
    @Override
    public List<AddressVO> getAddressList(String memberId) {
        List<AddressVO> addressVOList = addressPOMapper.getAddressList(memberId);
        return addressVOList;
    }
}
