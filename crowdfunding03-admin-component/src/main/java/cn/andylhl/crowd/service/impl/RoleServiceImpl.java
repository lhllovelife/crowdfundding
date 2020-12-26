package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.mapper.RoleMapper;
import cn.andylhl.crowd.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
