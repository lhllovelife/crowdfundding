package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.exception.SaveRoleException;
import cn.andylhl.crowd.exception.UpdateRoleException;
import cn.andylhl.crowd.mapper.RoleMapper;
import cn.andylhl.crowd.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * @Title: RoleServiceImpl
 * @Description: 角色业务实现类
 * @author: lhl
 * @date: 2020/12/26 15:49
 */

@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    public RoleMapper roleMapper;

    /**
     * 获取角色分页信息
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return
     */
    @Override
    public PageInfo<Role> getRolePageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 1. 开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 2. 执行查询
        List<Role> roleList = roleMapper.getRoleByKeyword(keyword);

        //3. 将结果数据封装到PageInfo中
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);

        return pageInfo;
    }

    /**
     * 保存角色对象信息
     * @param role
     */
    @Override
    public void saveRole(Role role) throws SaveRoleException {
        int count = roleMapper.insert(role);
        if (count == 0){
            throw new SaveRoleException("保存角色信息出现异常");
        }
    }

    /**
     * 更新角色对象名称
     * @param role
     */
    @Override
    public void updateRole(Role role) throws UpdateRoleException {

        try {
            roleMapper.updateByPrimaryKeySelective(role);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常类名: " + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new UpdateRoleException("抱歉！这个名称已经被使用了！");
            }
        }
    }

    /**
     * 根据id集合批量删除
     * @param roleIdList
     */
    @Override
    public void removeByRoleIdArray(List<String> roleIdList) {

        // 持久层，执行删除
        int count = roleMapper.removeByRoleIdArray(roleIdList);
        logger.info("删除角色名称个数：" + count);
    }

    /**
     * 查询已经分配的角色
     * @return
     */
    @Override
    public List<Role> getAssignedRole(String adminId) {
        return roleMapper.getAssignedRole(adminId);
    }

    /**
     * 查询未分配的角色
     * @return
     */
    @Override
    public List<Role> getUnAssignedRole(String adminId) {
        return roleMapper.getUnAssignedRole(adminId);
    }
}
