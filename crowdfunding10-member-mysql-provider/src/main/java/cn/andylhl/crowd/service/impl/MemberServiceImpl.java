package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.mapper.MemberPOMapper;
import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.service.MemberService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***
 * @Title: MemberServiceImpl
 * @Description:
 * @author: lhl
 * @date: 2021/1/14 13:38
 */

// 在类上使用该注解，并且设置为只读。将该类中所有业务方法设置为是只能读取的操作
// 需要增删改时候，需要针对具体方法上加注解
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    /**
     * 根据账号获取会员对象信息
     * @param loginacct
     * @return
     */
    @Override
    public MemberPO getMemberPOByLoginAcct(String loginacct) {
        return memberPOMapper.getMemberPOByLoginAcct(loginacct);
    }

    /**
     * 保存账号信息
     * @param memberPO
     */
    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = Exception.class,
            readOnly = false)
    @Override
    public void save(MemberPO memberPO) {
        memberPOMapper.insertSelective(memberPO);
//        int a = 15 / 0;
    }
}
