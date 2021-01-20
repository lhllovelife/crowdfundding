package cn.andylhl.crowd.mapper;


import cn.andylhl.crowd.po.ProjectItemPicPO;
import cn.andylhl.crowd.po.ProjectItemPicPOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProjectItemPicPOMapper {
    int countByExample(ProjectItemPicPOExample example);

    int deleteByExample(ProjectItemPicPOExample example);

    int deleteByPrimaryKey(String id);

    int insert(ProjectItemPicPO record);

    int insertSelective(ProjectItemPicPO record);

    List<ProjectItemPicPO> selectByExample(ProjectItemPicPOExample example);

    ProjectItemPicPO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ProjectItemPicPO record, @Param("example") ProjectItemPicPOExample example);

    int updateByExample(@Param("record") ProjectItemPicPO record, @Param("example") ProjectItemPicPOExample example);

    int updateByPrimaryKeySelective(ProjectItemPicPO record);

    int updateByPrimaryKey(ProjectItemPicPO record);

    // 保存项目id与详情图片id之间的关系
    void saveDetailPicturePathList(@Param("detailPicturePathMap") Map<String, String> detailPicturePathMap, @Param("projectid") String projectid);
}