package cn.andylhl.crowd;

import cn.andylhl.crowd.mapper.MemberPOMapper;
import cn.andylhl.crowd.po.MemberPO;
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
    private MemberPOMapper memberPOMapper;

    public static void main(String[] args) {
        SpringApplication.run(CrowdApplicaiton.class);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        MemberPO memberPO = new MemberPO(UUIDUtil.getUUID(), "ZS", "123", "法外狂徒", "zhangsan@qq.com", 1, 1, "张三", "1001200", 1);
//        int count = memberPOMapper.insert(memberPO);
//        System.out.println(count == 1 ? "成功" : "失败");
//    }
}
