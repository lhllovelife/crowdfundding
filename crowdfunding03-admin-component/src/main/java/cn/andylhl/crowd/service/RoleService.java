package cn.andylhl.crowd.service;

import cn.andylhl.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

/***
 * @Title: RoleService
 * @Description: 角色业务接口
 * @author: lhl
 * @date: 2020/12/26 15:49
 */
public interface RoleService {

    /**
     * 获取角色分页信息
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    PageInfo<Role> getRolePageInfo(Integer pageNum, Integer pageSize, String keyword);
}
