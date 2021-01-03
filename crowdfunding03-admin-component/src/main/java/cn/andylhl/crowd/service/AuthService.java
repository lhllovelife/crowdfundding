package cn.andylhl.crowd.service;

import cn.andylhl.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/***
 * @Title: AuthService
 * @Description: 权限分配相关业务
 * @author: lhl
 * @date: 2021/1/3 15:07
 */
public interface AuthService {

    /**
     * 查询所有Auth数据
     * @return
     */
    List<Auth> getAllAuth();

    /**
     * 根据roleId, 查询已经分配的权限
     * @param roleId
     * @return
     */
    List<Integer> getAssignedAuthIdListByRoleId(String roleId);

    /**
     * 保存角色与权限之间的关系
     * @param map
     */
    void saveRoleAuthRelationship(Map<String, List<String>> map);
}
