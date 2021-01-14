package cn.andylhl.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/***
 * @Title: CrowdApplication
 * @Description: 主启动类
 * @author: lhl
 * @date: 2021/1/14 17:12
 */

@EnableZuulProxy // 启用zull代理功能
@SpringBootApplication
public class CrowdApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplication.class);
    }

}
