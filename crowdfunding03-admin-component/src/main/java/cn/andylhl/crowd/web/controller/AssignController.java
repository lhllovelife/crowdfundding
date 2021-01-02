package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/***
 * @Title: AssignController
 * @Description: 权限分配控制器
 * @author: lhl
 * @date: 2021/1/1 14:48
 */

@Controller
public class AssignController {

    private Logger logger = LoggerFactory.getLogger(AssignController.class);

    @Resource
    private AdminService adminService;

    @Resource
    private RoleService roleService;

    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") String adminId, Model model) {

        logger.info("进入AssignController，跳转到角色信息分配页面");
        logger.info(adminId);

        // 1. 查询已经分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);

        // 2. 查询未分配的角色
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 将数据存入模型之中
        model.addAttribute("assignedRoleList", assignedRoleList);
        model.addAttribute("unAssignedRoleList", unAssignedRoleList);

        return "assign-role";
    }


    /**
     * 保存管理员与角色的关联关系
     * @param adminId
     * @param pageNum
     * @param keyword
     * @param roleIdList
     * @return
     */
    @RequestMapping("assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") String adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value = "roleIdList", required = false) List<String> roleIdList) {

        logger.info("进入AssignController，保存管理员与角色的关联关系");

        // 保存用户所关联的角色
        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
}
