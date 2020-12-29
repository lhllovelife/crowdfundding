package cn.andylhl.crowd.mapper;

import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.entity.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    int countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    // 根据关键词查询角色信息
    List<Role> getRoleByKeyword(@Param("keyword") String keyword);

    // 根据id集合批量删除
    int removeByRoleIdArray(List<String> roleIdList);
}