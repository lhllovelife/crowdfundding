package cn.andylhl.crowd;

import cn.andylhl.crowd.entity.Menu;
import cn.andylhl.crowd.service.MenuService;
import cn.andylhl.crowd.service.impl.MenuServiceImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/***
 * @Title: TestMenu
 * @Description:
 * @author: lhl
 * @date: 2020/12/30 9:58
 */
public class TestMenu {

    private Logger logger = LoggerFactory.getLogger(TestMenu.class);

    @Test
    public void testGetAll(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        MenuService menuService = (MenuService) ac.getBean("menuServiceImpl");
        List<Menu> menuList = menuService.getAll();
        int cnt = 0;
        for (Menu menu : menuList){
            logger.info( (++cnt) +  menu.toString());
        }
    }
}
