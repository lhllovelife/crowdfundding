package cn.andylhl.crowd.api;

import cn.andylhl.crowd.utils.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/***
 * @Title: RedisRemoteService
 * @Description: redis服务
 * @author: lhl
 * @date: 2021/1/14 16:03
 */

@FeignClient("crowd-redis") // 指定对应的微服务
public interface RedisRemoteService {

    /**
     * 将键值对存入redis
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(@RequestParam("key") String key, @RequestParam("value") String value);

    /**
     * 将键值对存入redis(带有生存时间)
     * @param key
     * @param value
     * @param time
     * @return
     */
    @RequestMapping("/set/redis/key/value/with/timeout/remote")
    ResultEntity<String> setRedisKeyValueWithTimeoutRemote(@RequestParam("key") String key,
                                                           @RequestParam("value") String value,
                                                           @RequestParam("time") Long time);

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    @RequestMapping("/get/redis/string/value/by/key/remote")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key);

    /**
     * 根据key删除
     * @param key
     * @return
     */
    @RequestMapping("/remove/redis/by/key/remote")
    ResultEntity<String> removeRedisByKeyRemote(@RequestParam("key") String key);
}
