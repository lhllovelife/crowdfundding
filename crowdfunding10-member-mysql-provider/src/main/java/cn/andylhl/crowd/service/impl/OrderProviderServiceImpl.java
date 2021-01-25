package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.mapper.*;
import cn.andylhl.crowd.po.AddressPO;
import cn.andylhl.crowd.po.OrderPO;
import cn.andylhl.crowd.po.OrderProjectPO;
import cn.andylhl.crowd.po.ReturnSupportPO;
import cn.andylhl.crowd.service.OrderProviderService;
import cn.andylhl.crowd.utils.UUIDUtil;
import cn.andylhl.crowd.vo.AddressVO;
import cn.andylhl.crowd.vo.OrderProjectVO;
import cn.andylhl.crowd.vo.OrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(OrderProviderServiceImpl.class);

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private ReturnSupportPOMapper returnSupportPOMapper;



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
        // 将项目id、回报id设置到orderProjectVO中
        orderProjectVO.setProjectId(projectId);
        orderProjectVO.setReturnId(returnId);

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

    /**
     * 保存订单数据
     * @param orderVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
    @Override
    public void saveOrderVO(OrderVO orderVO) {

        logger.info("msyql服务接收到的数据：" + orderVO.toString());

        //1. 创建orderPO
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderVO, orderPO);
        // 设置orderPO主键
        String orderId = UUIDUtil.getUUID();
        orderPO.setId(orderId);
        // 执行保存
        logger.info("存入数据库中的orderPO：" + orderPO.toString());
        orderPOMapper.insertSelective(orderPO);

        //2. 创建OrderProjectVO
        OrderProjectPO orderProjectPO = new OrderProjectPO();
        BeanUtils.copyProperties(orderVO.getOrderProjectVO(), orderProjectPO);
        // 设置主键和订单id
        orderProjectPO.setId(UUIDUtil.getUUID());
        orderProjectPO.setOrderId(orderId);
        // 执行保存
        logger.info("存入数据库中的orderProjectPO：" + orderProjectPO.toString());
        orderProjectPOMapper.insertSelective(orderProjectPO);


        // 3. 保存多条支持记录，在t_return_support
        ReturnSupportPO returnSupportPO = new ReturnSupportPO();
        int num = orderVO.getOrderProjectVO().getReturnCount();
        // 执行保存（用户支持几条则保存几条）
        for (int i = 1; i <= num; i++) {
            returnSupportPO.setId(UUIDUtil.getUUID());
            returnSupportPO.setProjectId(orderVO.getOrderProjectVO().getProjectId());
            returnSupportPO.setReturnId(orderVO.getOrderProjectVO().getReturnId());
            returnSupportPO.setMemberId(orderVO.getOrderProjectVO().getMemberId());
            returnSupportPOMapper.insertSelective(returnSupportPO);
        }
    }
}
/*
订单对象内容
OrderVO{
id='null',
orderNum='20210125194432f80e78b0f6b4462e9b91d8af20704996',
payOrderNum='null',
orderAmount=339.0,
invoice=1, invoiceTitle='公司',
orderRemark='备注1',
addressId='1c3ee19842544b879cebd0ba8d21643e',


orderProjectVO=OrderProjectVO{
id='null',
projectName='iphone12',
launchName='苹果公司',
returnContent='MagSafe 充电器',
returnCount=1,
count=3,
remainCount=1,
supportPrice=329,
freight=10,
orderId=null,
signalPurchase=1,
purchase=3,
projectId='aa918f862bb44642a7c5832cc5a54435',
returnId='66e13827ac61428498f9408a22904473',
memberId='e4563607d3b944ef82f91908baa7f4f9'}}



 */