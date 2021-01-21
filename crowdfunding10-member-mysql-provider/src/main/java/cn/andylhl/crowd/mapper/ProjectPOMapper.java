package cn.andylhl.crowd.mapper;

import cn.andylhl.crowd.po.ProjectPO;
import cn.andylhl.crowd.po.ProjectPOExample;
import cn.andylhl.crowd.vo.PortalProjectVO;
import cn.andylhl.crowd.vo.PortalTypeVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectPOMapper {
    int countByExample(ProjectPOExample example);

    int deleteByExample(ProjectPOExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProjectPO record);

    int insertSelective(ProjectPO record);

    List<ProjectPO> selectByExample(ProjectPOExample example);

    ProjectPO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByExample(@Param("record") ProjectPO record, @Param("example") ProjectPOExample example);

    int updateByPrimaryKeySelective(ProjectPO record);

    int updateByPrimaryKey(ProjectPO record);

    // 先获取所有分类数据
    List<PortalTypeVO> getAllType();

    // 根据分类id查询该分类下所有的项目
    List<PortalProjectVO> getAllProjectByTypeId(String typeid);
}