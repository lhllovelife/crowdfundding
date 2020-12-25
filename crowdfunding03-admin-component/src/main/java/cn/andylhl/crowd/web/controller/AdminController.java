package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.exception.DeleteAdminException;
import cn.andylhl.crowd.exception.LoginFailedException;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.utils.UUIDUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Resource
    private AdminService adminService;

    @RequestMapping("/admin/save.json")
    public @ResponseBody Object save(Admin admin){
        logger.info("执行保存管理员方法");
        admin.setId(UUIDUtil.getUUID());
        admin.setCreateTime(DateUtil.format(new Date(), Constant.DATE_Format_ALL));
        logger.info(admin.toString());
        int count = adminService.save(admin);
        if (count == 1){
            return ResultEntity.successWithData(count) ;
        }
        else {
            return ResultEntity.failed("插入数据失败") ;
        }
    }


    @RequestMapping("/admin/do/login.html")
    public String doLogin(String loginAcct, String userPswd, HttpServletRequest request) throws LoginFailedException {
        logger.info("进入AdminController, 验证管理员登录信息");
        logger.info("账号：" + loginAcct);
        logger.info("密码：" + userPswd);

        Admin admin = adminService.getAdminByLoginAcct(loginAcct, userPswd);
        // 执行到这里，说明未出现登录失败异常，登录验证成功
        // 将查询到的管理员对象存入session域中
        // 检测session是否存在，如果不存在，则新建一个新的会话对象
        HttpSession session = request.getSession();
        session.setAttribute(Constant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpServletRequest request){
        logger.info("进入AdminController, 执行退出登录");
        HttpSession session = request.getSession(false);
        logger.info("session: " +  session);
        if (session != null){
            //服务器端存在会话对象，则销毁session
            session.invalidate();
        }
        //重定向到登录页面
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 处理分页查询管理员信息（带关键词或不带关键词）
     * @param pageNum 页码
     * @param pageSize 每页显示条数
     * @param keyword 关键词
     * @return
     */
    @RequestMapping("/admin/get/page.html")
    public String getAdminPageInfo(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                 @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                                 Model model){
        // 获取装有分页信息的对象
        PageInfo<Admin> pageInfo = adminService.getAdminPageInfo(pageNum, pageSize, keyword);
        // 将分页信息对象放到模型中，跳转到页面
        model.addAttribute(Constant.ATTR_NAME_PAGE_INFO, pageInfo);
        //跳转到admin-page.jsp
        return "admin-page";
    }

    @RequestMapping("/admin/remove.html")
    public String removeAdminById(String adminId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "keyword", defaultValue = "") String keyword,
                                  HttpServletRequest request) throws DeleteAdminException {
        //执行单条删除
        adminService.removeAdminById(adminId, request);
        // 执行到这里说明删除成功，未出现异常
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

}
