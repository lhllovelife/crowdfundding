package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.exception.SaveRoleException;
import cn.andylhl.crowd.exception.UpdateRoleException;
import cn.andylhl.crowd.service.RoleService;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.utils.UUIDUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/***
 * @Title: RoleController
 * @Description: 角色控制器
 * @author: lhl
 * @date: 2020/12/26 15:48
 */

@Controller
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private RoleService roleService;

    /**
     * 获取角色分页信息
     * @return
     */
    @RequestMapping("/role/get/page/info.json")
    public @ResponseBody ResultEntity<PageInfo<Role>> getRolePageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword){
        logger.info("进入RoleController,获取角色分页信息");

        PageInfo<Role> pageInfo =  roleService.getRolePageInfo(pageNum, pageSize, keyword);

        // 将pageInfo封装到结果集合中
        return ResultEntity.successWithData(pageInfo);
    }

    @RequestMapping("/role/save.json")
    public @ResponseBody Object saveRole(Role role) throws SaveRoleException {
        logger.info("进入RoleController,保存角色信息");
        // 封装信息
        role.setId(UUIDUtil.getUUID());
        role.setCreateTime(DateUtil.format(new Date(), Constant.DATE_Format_ALL));
        // 保存角色对象
        roleService.saveRole(role);

        //执行到这里说明未出现异常
        return ResultEntity.successWithoutData();
    }

    /**
     * 更新角色信息
     * @param role
     * @return
     */
    @RequestMapping("/role/update.json")
    public @ResponseBody Object updateRole(Role role) throws UpdateRoleException {
        logger.info("进入RoleController,更新角色信息");

        roleService.updateRole(role);

        return ResultEntity.successWithoutData();
    }

    /**
     * 根据roleIdList集合中的id，批量删除角色
     * @param roleIdList
     * @return
     */
    @RequestMapping("/role/delete/by/role/id/array.json")
    public @ResponseBody Object removeByRoleIdArray(@RequestBody List<String> roleIdList){
        logger.info("进入RoleController,删除角色信息");
        // 批量删除
        roleService.removeByRoleIdArray(roleIdList);

        return ResultEntity.successWithoutData();
    }

}
