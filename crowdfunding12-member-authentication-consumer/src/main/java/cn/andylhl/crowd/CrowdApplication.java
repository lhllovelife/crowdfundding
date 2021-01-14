package cn.andylhl.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

/***
 * @Title: CrowdApplication
 * @Description: 主启动类
 * @author: lhl
 * @date: 2021/1/14 16:47
 */

@EnableDiscoveryClient // 启用eureka客户端，当前版本可以不写
@SpringBootApplication
public class CrowdApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplication.class);
    }

}
