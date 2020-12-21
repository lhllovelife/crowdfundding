package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.mapper.AdminMapper;
import cn.andylhl.crowd.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * @Title: AdminServiceImpl
 * @Description: 管理员业务实现类
 * @author: lhl
 * @date: 2020/12/21 15:25
 */

@Service
public class AdminServiceImpl implements AdminService {

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
}
