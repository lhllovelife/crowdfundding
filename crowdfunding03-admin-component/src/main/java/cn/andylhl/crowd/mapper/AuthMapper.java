package cn.andylhl.crowd.mapper;

import cn.andylhl.crowd.entity.Auth;
import cn.andylhl.crowd.entity.AuthExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    // 根据roleId, 查询已经分配的权限
    List<Integer> getAssignedAuthIdListByRoleId(String roleId);

    // 保存角色与权限之间的关系
    Integer saveNewRelationship(@Param("roleId") String roleId, @Param("uuidAuthIdMap") Map<String, String> uuidAuthIdMap);

    // 根据roleId删除其所有权限相关数据
    Integer removeAllByRoleId(String roleId);
}