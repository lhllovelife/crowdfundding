package cn.andylhl.crowd.service;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.exception.DeleteAdminException;
import cn.andylhl.crowd.exception.LoginAcctAlreadyInUseException;
import cn.andylhl.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import cn.andylhl.crowd.exception.LoginFailedException;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/***
 * @Title: AdminService
 * @Description: 管理员业务接口
 * @author: lhl
 * @date: 2020/12/21 15:24
 */
public interface AdminService {

    /**
     * 保存管理员信息
     * @param admin
     * @return
     */
    int save(Admin admin);

    /**
     * 验证登录
     * @param loginAcct
     * @param userPswd
     * @return
     */
    Admin getAdminByLoginAcct(String loginAcct, String userPswd) throws LoginFailedException;

    /**
     * 获取装有用户分页信息的对象
     * @return
     * @param pageNum
     * @param pageSize
     * @param keyword
     */
    PageInfo<Admin> getAdminPageInfo(Integer pageNum, Integer pageSize, String keyword);

    /**
     * 根据id删除管理员信息
     * @param adminId
     */
    void removeAdminById(String adminId, HttpServletRequest request) throws DeleteAdminException;

    /**
     * 新增用户
     * @param admin
     */
    void saveAdmin(Admin admin) throws LoginAcctAlreadyInUseException;

    /**
     * 根据adminId查询单条信息
     * @param adminId
     * @return
     */
    Admin getAdminById(String adminId);

    /**
     * 更新管理员信息
     * @param admin
     */
    void updateAdminById(Admin admin) throws LoginAcctAlreadyInUseForUpdateException;

    /**
     * 保存用户所关联的角色
     * @param adminId
     * @param roleIdList
     */
    void saveAdminRoleRelationship(String adminId, List<String> roleIdList);
}
