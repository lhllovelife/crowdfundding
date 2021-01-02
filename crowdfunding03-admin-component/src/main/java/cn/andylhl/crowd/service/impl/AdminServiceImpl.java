package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.exception.DeleteAdminException;
import cn.andylhl.crowd.exception.LoginAcctAlreadyInUseException;
import cn.andylhl.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import cn.andylhl.crowd.exception.LoginFailedException;
import cn.andylhl.crowd.mapper.AdminMapper;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.MD5Util;
import cn.andylhl.crowd.utils.UUIDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/***
 * @Title: AdminServiceImpl
 * @Description: 管理员业务实现类
 * @author: lhl
 * @date: 2020/12/21 15:25
 */

@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    //注入dao
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 保存管理员信息
     * @param admin
     * @return
     */
    @Override
    public int save(Admin admin) {
        int count = adminMapper.insert(admin);
        return count;
    }

    /**
     * 验证登录
     * @param loginAcct 表单提交管理员账号
     * @param userPswd 表单管理员提交密码
     * @return
     */
    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) throws LoginFailedException {
        //1. 根据账号查询数据库中该管理员信息
        Admin admin = adminMapper.getAdminByLoginAcct(loginAcct);
        logger.info("管理员信息：" + admin);
        //2. 判断该账号是否存在
        if (admin == null){
            throw new LoginFailedException("该账号不存在，请您检查您输入的账号信息");
        }
        //3. 将用户密码进行加密
        String userPswdForm = MD5Util.getMD5(userPswd);
        String userPswdDb = admin.getUserPswd();
        //4. 判断密码是否一致
        if (!Objects.equals(userPswdForm, userPswdDb)){
            //密码不一致
            throw new LoginFailedException("密码有误");
        }
        //执行到这里，说明用户名和密码信息均一致，将管理员信息对象返回
        return admin;
    }

    /**
     * 获取分页信息对象
     * @param pageNum 页码
     * @param pageSize 每页显示信息条数
     * @param keyword 关键词
     * @return
     */
    @Override
    public PageInfo<Admin> getAdminPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 1. 开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 2. 执行查询
        List<Admin> adminList = adminMapper.getAdminByKeyword(keyword);
        //3. adminList封装到PageInfo
        PageInfo<Admin> adminPageInfo = new PageInfo<>(adminList);

        return adminPageInfo;
    }

    /**
     * 根据id删除管理员信息（该删除方法配置有事务，出现异常会回滚）
     * @param adminId
     */
    @Override
    public void removeAdminById(String adminId, HttpServletRequest request) throws DeleteAdminException {
        // 1. 获取当前登录账户的id
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(Constant.ATTR_NAME_LOGIN_ADMIN);
        // 2. 判断要删除的账号与当前账号是否是同一个
        if (Objects.equals(adminId, admin.getId())){
            //相等则抛出异常
            throw new DeleteAdminException("不允许自己删除自己的账号");
        }

        int count = adminMapper.deleteByPrimaryKey(adminId);
        if (count == 0){
            throw new DeleteAdminException("删除管理员信息异常");
        }
        return;
    }

    /**
     * 新增用户
     * @param admin
     */
    @Override
    public void saveAdmin(Admin admin) throws LoginAcctAlreadyInUseException {
        // 1. 获取用户设置的密码
        String userPswd = admin.getUserPswd();
        // 2. 将密码进行加密
        userPswd = MD5Util.getMD5(userPswd);
        // 3. 将加密后密码存储到对象中
        admin.setUserPswd(userPswd);
        admin.setId(UUIDUtil.getUUID());
        admin.setCreateTime(DateUtil.format(new Date(), Constant.DATE_Format_ALL));
        //4. 持久化到数据库中
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常类名：" + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(Constant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    /**
     * 根据adminId查询单条信息
     * @param adminId
     * @return
     */
    @Override
    public Admin getAdminById(String adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 更新管理员信息
     * @param admin
     */
    @Override
    public void updateAdminById(Admin admin) throws LoginAcctAlreadyInUseForUpdateException {

        try {
            int count = adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常类名：" + e.getClass().getName());
            if (e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseForUpdateException("抱歉！这个账号已经被使用了！");
            }
        }
        return;
    }

    /**
     * 保存用户所关联的角色
     * @param adminId
     * @param roleIdList
     */
    @Override
    public void saveAdminRoleRelationship(String adminId, List<String> roleIdList) {

        // 1. 根据id删除旧的角色关联
        adminMapper.removeOldRelationship(adminId);

        // 2. 保存新的角色关系
        if (roleIdList != null && roleIdList.size() > 0) {

            // 生成uuiid与每条角色关系对应
            Map<String, String> idRoleIdMap = new HashMap<>();
            for (String roleId : roleIdList) {
                idRoleIdMap.put(UUIDUtil.getUUID(), roleId);
            }
            adminMapper.saveNewRelationship(adminId, idRoleIdMap);
        }

    }
}
