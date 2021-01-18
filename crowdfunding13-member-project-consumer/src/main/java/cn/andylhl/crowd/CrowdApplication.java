package cn.andylhl.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/***
 * @Title: CrowdApplication
 * @Description: 主启动类
 * @author: lhl
 * @date: 2021/1/18 14:21
 */

@EnableFeignClients // 启用Feign客户端
@EnableDiscoveryClient
@SpringBootApplication
public class CrowdApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplication.class);
    }
}
