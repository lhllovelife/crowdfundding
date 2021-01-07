package cn.andylhl.crowd;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.mapper.AdminMapper;
import cn.andylhl.crowd.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

/***
 * @Title: TestAdmin
 * @Description:
 * @author: lhl
 * @date: 2020/12/24 14:36
 */
public class TestAdmin {

    @Test
    public void testAdminMapper(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        AdminMapper adminMapper = (AdminMapper) ac.getBean("adminMapper");
        List<Admin> adminList = adminMapper.getAdminByKeyword("");
        for (Admin admin : adminList){
            System.out.println(admin);
        }
    }

    @Test
    public void testAdminService(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        AdminService adminService = (AdminService) ac.getBean("adminServiceImpl");
        PageInfo<Admin> pageInfo = adminService.getAdminPageInfo(1, 5, "w");
        List<Admin> adminList = pageInfo.getList();
        System.out.println(pageInfo.getPageNum());
        for (Admin admin : adminList){
            System.out.println(admin);
        }

    }


    @Test
    public void testAdminService2(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        AdminService adminService = (AdminService) ac.getBean("adminServiceImpl");
        Admin admin = adminService.getAdminByLoginAcct("zs5");
        System.out.println(admin);

    }
    @Test

    public void testAdminService3(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        BCryptPasswordEncoder passwordEncoder = (BCryptPasswordEncoder) ac.getBean("bCryptPasswordEncoder");
        System.out.println(passwordEncoder);
        System.out.println(passwordEncoder.encode("123"));


    }
}
