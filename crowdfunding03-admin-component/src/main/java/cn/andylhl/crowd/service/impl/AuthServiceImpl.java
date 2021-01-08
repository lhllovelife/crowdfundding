package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.entity.Auth;
import cn.andylhl.crowd.entity.AuthExample;
import cn.andylhl.crowd.mapper.AuthMapper;
import cn.andylhl.crowd.service.AuthService;
import cn.andylhl.crowd.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * @Title: AuthServiceImpl
 * @Description: 权限控制业务实现类
 * @author: lhl
 * @date: 2021/1/3 15:08
 */

@Service
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private AuthMapper authMapper;

    /**
     * 查询所有Auth数据
     * @return
     */
    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(new AuthExample());
    }

    /**
     * 根据roleId, 查询已经分配的权限
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getAssignedAuthIdListByRoleId(String roleId) {

        return authMapper.getAssignedAuthIdListByRoleId(roleId);
    }

    /**
     * 保存角色与权限之间的关系
     * @param map [{authArray=[1, 2, 3, 4, 5, 6, 7], roleId=[b73df83945354bd0a1d91e8e1ff62afc]}
     */
    @Override
    public void saveRoleAuthRelationship(Map<String, List<String>> map) {

        String roleId = map.get("roleId").get(0);
        List<String> authArray = map.get("authArray");

        // 根据roleId删除其所有权限相关数据
        Integer removeNum = authMapper.removeAllByRoleId(roleId);
        logger.info("删除关系数目：" + removeNum);

        // 当authArray有被勾选时候,authArray有数据，则进行保存
        if (authArray != null && authArray.size() > 0) {

            // 生成uuid与每条authId对应
            Map<String, String> uuidAuthIdMap = new HashMap<>();
            for (String authId : authArray) {
                uuidAuthIdMap.put(UUIDUtil.getUUID(), authId);
            }

            Integer count = authMapper.saveNewRelationship(roleId, uuidAuthIdMap);
            logger.info("保存成功数目：" + count);
            System.out.println(count);

        }

    }


    @Override
    public List<String> getAssignedAuthNameListByAdminId(String adminId) {
        return authMapper.getAssignedAuthNameListByAdminId(adminId);
    }
}
