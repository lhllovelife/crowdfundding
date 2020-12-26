package cn.andylhl.crowd;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.mapper.RoleMapper;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.UUIDUtil;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/***
 * @Title: TestRole
 * @Description: 角色相关测试
 * @author: lhl
 * @date: 2020/12/26 16:40
 */
public class TestRole {

    @Test
    public void addRole() throws InterruptedException {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        RoleMapper roleMapper = (RoleMapper) ac.getBean("roleMapper");
        for (int i = 1; i <= 38; i++){
            Thread.sleep(1000);
            roleMapper.insert(new Role(UUIDUtil.getUUID(), "role"+i , DateUtil.format(new Date(), Constant.DATE_Format_ALL)));
        }
    }
}
