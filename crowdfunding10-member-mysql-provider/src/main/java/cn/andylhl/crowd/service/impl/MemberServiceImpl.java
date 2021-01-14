package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.mapper.MemberPOMapper;
import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.service.MemberService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Title: MemberServiceImpl
 * @Description:
 * @author: lhl
 * @date: 2021/1/14 13:38
 */

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
}
