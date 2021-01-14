package cn.andylhl.crowd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.swing.*;

/***
 * @Title: CrowdApplicaiton
 * @Description: 主启动类
 * @author: lhl
 * @date: 2021/1/14 14:03
 */

@SpringBootApplication
public class CrowdApplicaiton implements CommandLineRunner {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplicaiton.class);
    }

    @Override
    public void run(String... args) throws Exception {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        operations.set("hello", "word");

    }
}
