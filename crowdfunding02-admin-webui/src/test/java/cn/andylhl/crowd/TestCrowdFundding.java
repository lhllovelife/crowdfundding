package cn.andylhl.crowd;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.mapper.AdminMapper;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.MD5Util;
import cn.andylhl.crowd.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/***
 * @Title: TestCrowdFundding
 * @Description:
 * @author: lhl
 * @date: 2020/12/20 16:01
 */

//Spring整和junit用于单元测试
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class TestCrowdFundding {

    private Logger logger = LoggerFactory.getLogger(TestCrowdFundding.class);

    //可以在类中注入ioc容器的对象
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testTx() throws InterruptedException {
        for (int i = 1; i <= 38; i++){
            int count = adminService.save(new Admin(UUIDUtil.getUUID(), "zs"+i, MD5Util.getMD5("123"), "张三"+i,
                    "zs"+i+"@qq.com", DateUtil.format(new Date(), Constant.DATE_Format_ALL)));
            Thread.sleep(1000);
        }


    }

    @Test
    public void testConnect(){
        Admin admin = new Admin(UUIDUtil.getUUID(), "zs", "123", "张三", "zs@qq.com", DateUtil.format(new Date(), Constant.DATE_Format_ALL));
        int count = adminMapper.insert(admin);
        System.out.println(count == 1 ? "插入成功" : "插入失败");
    }

    @Test
    public void testLogger(){
        //获取打印日志信息的对象，这里传入的Class就是当前打印日志的类
        Logger logger =  LoggerFactory.getLogger(TestCrowdFundding.class);

        logger.debug("debug level");
        logger.debug("debug level");
        logger.debug("debug level");

        logger.info("info level");
        logger.info("info level");
        logger.info("info level");

        logger.warn("warn level");
        logger.warn("warn level");
        logger.warn("warn level");

        logger.error("error level");
        logger.error("error level");
        logger.error("error level");

    }
}
