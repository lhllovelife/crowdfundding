package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.utils.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/***
 * @Title: RedisController
 * @Description: redis数据服务
 * @author: lhl
 * @date: 2021/1/14 16:21
 */

@RestController
public class RedisController {

    private Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 将键值对存入redis
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote")
    public ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value) {
        logger.info("redis服务，将键值对存入");

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value);
            // 执行成功
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    /**
     * 将键值对存入redis(带有生存时间)
     * @param key
     * @param value
     * @param time
     * @return
     */
    @RequestMapping("/set/redis/key/value/with/timeout/remote")
    public ResultEntity<String> setRedisKeyValueWithTimeoutRemote(@RequestParam("key") String key,
                                                           @RequestParam("value") String value,
                                                           @RequestParam("time") Long time) {
        logger.info("redis服务，将键值对存入（带有生存时间）");

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            operations.set(key, value, time, TimeUnit.SECONDS);
            // 执行成功
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    @RequestMapping("/get/redis/string/value/by/key/remote")
    public ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key) {

        logger.info("redis服务，根据key获取value");

        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            String value = operations.get(key);
            // 执行成功
            return ResultEntity.successWithData(value);

        } catch (Exception e) {
            e.printStackTrace();
            // 执行失败
            return ResultEntity.failed(e.getMessage());
        }

    }

    /**
     * 根据key删除
     * @param key
     * @return
     */
    @RequestMapping("/remove/redis/by/key/remote")
    public ResultEntity<String> removeRedisByKeyRemote(@RequestParam("key") String key) {
        logger.info("redis服务，根据key删除单条");

        try {
            redisTemplate.delete(key);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

}
