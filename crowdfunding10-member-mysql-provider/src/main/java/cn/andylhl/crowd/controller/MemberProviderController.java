package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.service.MemberService;
import cn.andylhl.crowd.utils.ResultEntity;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 * @Title: MemberProviderController
 * @Description: 会员相关数据服务
 * @author: lhl
 * @date: 2021/1/14 13:29
 */

@RestController
public class MemberProviderController {

    private Logger logger = LoggerFactory.getLogger(MemberProviderController.class);

    @Resource
    private MemberService memberService;

    /**
     * 根据账号获取会员对象
     * @param loginacct
     * @return
     */
    @RequestMapping("/get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct) {
        logger.info("mysql-provider服务，根据账号获取会员对象");
        logger.info("参数：" + loginacct);
        try {
            MemberPO memberPO = memberService.getMemberPOByLoginAcct(loginacct);
            // 执行查询成功
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            // 执行查询失败
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 保存账号信息
     * @param memberPO
     * @return
     */
    @RequestMapping("/save/memberpo/remote")
    public ResultEntity<String> saveMemberPORemote(@RequestBody MemberPO memberPO) {
        logger.info("mysql-provider服务，保存账号信息");
        logger.info(memberPO.toString());

        try {
            memberService.save(memberPO);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            // 保存失败，返回错误消息
            return ResultEntity.failed(e.getMessage());
        }
    }

}
