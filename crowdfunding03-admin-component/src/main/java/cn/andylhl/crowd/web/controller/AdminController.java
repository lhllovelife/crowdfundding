package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.utils.Const;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/***
 * @Title: AdminController
 * @Description: 管理员控制器
 * @author: lhl
 * @date: 2020/12/22 12:48
 */

@Controller
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/save.do")
    public @ResponseBody Object save(Admin admin){
        logger.info("执行保存管理员方法");
        admin.setId(UUIDUtil.getUUID());
        admin.setCreateTime(DateUtil.format(new Date(), Const.DATE_Format_ALL));
        logger.info(admin.toString());
        int count = adminService.save(admin);
        if (count == 1){
            return ResultEntity.successWithData(count) ;
        }
        else {
            return ResultEntity.failed("插入数据失败") ;
        }
    }
}
