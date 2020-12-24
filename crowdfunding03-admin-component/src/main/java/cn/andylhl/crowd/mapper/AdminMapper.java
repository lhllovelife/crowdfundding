package cn.andylhl.crowd.mapper;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.entity.AdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(String id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    // 根据账号查询管理员信息
    Admin getAdminByLoginAcct(String loginAcct);

    // 根据关键字进行查询管理员对象信息
    List<Admin> getAdminByKeyword(@Param("keyword") String keyword);
}