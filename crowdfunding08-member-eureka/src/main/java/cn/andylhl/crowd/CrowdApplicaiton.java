package cn.andylhl.crowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/***
 * @Title: CrowdApplicaiton
 * @Description:
 * @author: lhl
 * @date: 2021/1/14 9:47
 */

@EnableEurekaServer // 启用Eureka服务端
@SpringBootApplication // 开启SpringBoot配置
public class CrowdApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplicaiton.class);
    }

}
