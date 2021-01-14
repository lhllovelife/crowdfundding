package cn.andylhl.crowd.service;

import cn.andylhl.crowd.po.MemberPO;

/***
 * @Title: MemberService
 * @Description: 会员业务
 * @author: lhl
 * @date: 2021/1/14 13:37
 */
public interface MemberService {


    /**
     *  根据账号获取会员对象信息
     * @param loginacct
     * @return
     */
    MemberPO getMemberPOByLoginAcct(String loginacct);
}
