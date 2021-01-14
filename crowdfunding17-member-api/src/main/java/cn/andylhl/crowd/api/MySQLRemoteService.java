package cn.andylhl.crowd.api;

import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.utils.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * @Title: MySQLRemoteService
 * @Description: mysql服务接口
 * @author: lhl
 * @date: 2021/1/14 13:24
 */

@FeignClient("crowd-mysql") // 指定对应的微服务名称
public interface MySQLRemoteService {

    /**
     * 根据账号获取会员对象
     * @param loginacct
     * @return
     */
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

}
