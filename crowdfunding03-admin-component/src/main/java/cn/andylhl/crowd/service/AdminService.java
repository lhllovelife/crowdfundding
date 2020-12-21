package cn.andylhl.crowd.service;

import cn.andylhl.crowd.entity.Admin;

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
}
