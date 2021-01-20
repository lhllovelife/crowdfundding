package cn.andylhl.crowd.api;

import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    /**
     * 保存账号信息
     * @param memberPO
     * @return
     */
    @RequestMapping("/save/memberpo/remote")
    ResultEntity<String> saveMemberPORemote(@RequestBody MemberPO memberPO);

    /**
     * 将项目信息持久化到数据库
     * @param projectVO
     * @param memberid
     * @return
     */
    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberid") String memberid);
}
