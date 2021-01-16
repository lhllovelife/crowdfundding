package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.MySQLRemoteService;
import cn.andylhl.crowd.api.RedisRemoteService;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.utils.UUIDUtil;
import cn.andylhl.crowd.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.beans.Beans;
import java.util.Objects;

/***
 * @Title: MemberController
 * @Description: 会员控制器
 * @author: lhl
 * @date: 2021/1/15 16:35
 */

@Controller
public class MemberController {

    Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Resource
    private RedisRemoteService redisRemoteService;

    @Resource
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 发送验证码到用户手机
     * @param email
     * @return
     */
    @RequestMapping("/auth/member/send/short/message")
    public @ResponseBody ResultEntity<String> sendShortMessage(@RequestParam("email") String email) {
        logger.info("crowd-auth服务, 发送验证码到用户邮箱");
        logger.info(email);

        // 1. 调用工具类，生成验证码并发送到用户邮箱，将生成的验证码返回
        ResultEntity<String> messageResultEntity = null;
        try {
            messageResultEntity = CrowdUtil.sendVerifyCodeByEmail("2432707158@qq.com", email, "2432707158", "ajlzgiopwcnheaie");
        } catch (Exception e) {
            e.printStackTrace();
            // 如果验证码发送失败，则返回错误消息到页面
            return ResultEntity.failed(messageResultEntity.getMessage());
        }

        // 2. 将验证码存储到redis中
        String key = Constant.REDIS_CODE_PREFIX + email;
        String verifyCode = messageResultEntity.getData();
        ResultEntity<String> redisResultEntity = redisRemoteService.setRedisKeyValueWithTimeoutRemote(key, verifyCode, 120L);

        // 如果redis插入异常
        if (ResultEntity.FAILED.equals(redisResultEntity.getResult())) {
            return ResultEntity.failed(redisResultEntity.getMessage());
        }

        // 执行到这里，说明发送成功，且成功将数据存储到redis中
        return ResultEntity.successWithData(verifyCode);
    }


    @RequestMapping("/auth/do/member/register")
    public String memberRegister(MemberVO memberVO, Model model) {
        logger.info("crowd-auth服务, 进行新用户的注册");
        logger.info(memberVO.toString());

        // 1. 到redis中查询该邮箱地址对应的验证码
        String email = memberVO.getEmail();
        String key = Constant.REDIS_CODE_PREFIX + email;
        ResultEntity<String> redisResultEntity = redisRemoteService.getRedisStringValueByKeyRemote(key);
        // 判断是否查询到
        if (ResultEntity.FAILED.equals(redisResultEntity.getResult())) {
            // 如果找不到
            logger.info("未能成功从redis获取到值");
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, redisResultEntity.getMessage());
            return "member-reg";
        }

        // 2.判断从redis获取的值是否为空
        String codeFromForm = memberVO.getVerifyCode();
        String codeFromRedis = redisResultEntity.getData();
        if (codeFromRedis == null) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, "验证码已失效！请检查邮箱地址是否正确或重新发送");
            return "member-reg";
        }

        // 3. 判断验证码是否一致
        // 若不一致，则返回错误信息
        if (!Objects.equals(codeFromForm, codeFromRedis)) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, "验证码不一致，请检查后重新输入");
            return "member-reg";
        }
        // 若一致，则删除redis中的验证码
        logger.info("验证码校验一致，删除redis中的验证码");
        ResultEntity<String> removeRedisResultEntity = redisRemoteService.removeRedisByKeyRemote(key);
        if (ResultEntity.FAILED.equals(removeRedisResultEntity.getResult())) {
            logger.info("验证码校验一致后，未成功删除redis中的验证码");
        }

        // 4. 进行密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswdBeforeEncoder = memberVO.getUserpswd();
        String userpswdAfterEncoder = passwordEncoder.encode(userpswdBeforeEncoder);
        memberVO.setUserpswd(userpswdAfterEncoder);

        // 5. 将数据保存到mysql中

        // 准备数据
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);
        // 设置主键
        memberPO.setId(UUIDUtil.getUUID());

        // 根据账号查询是否已经有重复账号
        ResultEntity<MemberPO> memberPOResultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(memberPO.getLoginacct());
        if (ResultEntity.SUCCESS.equals(memberPOResultEntity.getResult()) && memberPOResultEntity.getData() != null) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, "该账号已经被注册");
            return "member-reg";
        }


        // 调用mysql-provider提供的服务执行保存
        ResultEntity<String> mysqlResultEntity = mySQLRemoteService.saveMemberPORemote(memberPO);
        // 若保存失败
        if (ResultEntity.FAILED.equals(mysqlResultEntity.getResult())) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, "mysql服务，数据保存失败");
            return "member-reg";
        }
        // 6. 保存成功则重定向到登录页
        return "redirect:/auth/member/to/login/page?register=ok";
    }

}
