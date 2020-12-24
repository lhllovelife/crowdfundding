package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.exception.LoginFailedException;
import cn.andylhl.crowd.mapper.AdminMapper;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.utils.MD5Util;
import cn.andylhl.crowd.web.controller.AdminController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
}
