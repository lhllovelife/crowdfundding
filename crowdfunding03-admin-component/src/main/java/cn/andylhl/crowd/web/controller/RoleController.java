package cn.andylhl.crowd.web.controller;

import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.service.RoleService;
import cn.andylhl.crowd.utils.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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

}
