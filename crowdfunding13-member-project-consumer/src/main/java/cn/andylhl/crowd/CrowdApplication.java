package cn.andylhl.crowd;

import cn.andylhl.crowd.config.OSSProperties;
import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileInputStream;
import java.io.InputStream;

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

    @Autowired
    private OSSProperties ossProperties;

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplication.class);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        String path = Thread.currentThread().getContextClassLoader().getResource("math.png").getPath();
//        InputStream inputStream = new FileInputStream(path);
//        System.out.println("inputstream: " + inputStream);
//        ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
//                ossProperties.getAccessKeyId(),
//                ossProperties.getAccessKeySecret(),
//                inputStream,
//                ossProperties.getBucketName(),
//                ossProperties.getBucketDomain(),
//                "math.png");
//        System.out.println(resultEntity);
//    }
}
