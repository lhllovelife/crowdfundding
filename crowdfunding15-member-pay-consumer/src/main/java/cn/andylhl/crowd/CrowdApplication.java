package cn.andylhl.crowd;

import cn.andylhl.crowd.config.PayProperties;
import cn.andylhl.crowd.controller.PayController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

/***
 * @Title: CrowdApplication
 * @Description: 主启动类
 * @author: lhl
 * @date: 2021/1/24 22:30
 */

@EnableFeignClients // 启用Feign客户端
@EnableDiscoveryClient
@SpringBootApplication
public class CrowdApplication {


    public static void main(String[] args) {
        SpringApplication.run(CrowdApplication.class);
    }
}
