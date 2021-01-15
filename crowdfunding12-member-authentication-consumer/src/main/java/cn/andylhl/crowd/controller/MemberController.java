package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.RedisRemoteService;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

    @RequestMapping("/auth/member/send/short/message")
    public @ResponseBody ResultEntity<String> sendShortMessage(@RequestParam("phoneNum") String phoneNum) {
        logger.info("crowd-auth服务, 发送验证码给用户手机");
        logger.info(phoneNum);

        // 1. 调用工具类，生成验证码并发送到用户手机（模拟），将生成的验证码返回
        ResultEntity<String> messageResultEntity = CrowdUtil.sendMessage();

        // 如果验证码发送失败，返回错误消息
        if (ResultEntity.FAILED.equals(messageResultEntity.getResult())) {
            return ResultEntity.failed(messageResultEntity.getMessage());
        }

        // 2. 将验证码存储到redis中
        String key = Constant.REDIS_CODE_PREFIX + phoneNum;
        String verifyCode = messageResultEntity.getData();
        ResultEntity<String> redisResultEntity = redisRemoteService.setRedisKeyValueWithTimeoutRemote(key, verifyCode, 120L);

        // 如果redis插入异常
        if (ResultEntity.FAILED.equals(redisResultEntity.getResult())) {
            return ResultEntity.failed(redisResultEntity.getMessage());
        }

        // 执行到这里，说明发送成功，且成功将数据存储到redis中
        return ResultEntity.successWithData(verifyCode);
    }

}
