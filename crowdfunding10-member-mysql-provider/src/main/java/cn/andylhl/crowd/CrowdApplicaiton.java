package cn.andylhl.crowd;

import cn.andylhl.crowd.mapper.MemberPOMapper;
import cn.andylhl.crowd.po.MemberPO;
import cn.andylhl.crowd.service.ProjectProviderService;
import cn.andylhl.crowd.utils.UUIDUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;


/***
 * @Title: CrowdApplicaiton
 * @Description:
 * @author: lhl
 * @date: 2021/1/14 9:47
 */

@MapperScan(basePackages = "cn.andylhl.crowd.mapper") // 扫描mapper文件
@SpringBootApplication // 开启SpringBoot配置
public class CrowdApplicaiton {

    @Autowired
    private ProjectProviderService projectProviderService;

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplicaiton.class);
    }

}
