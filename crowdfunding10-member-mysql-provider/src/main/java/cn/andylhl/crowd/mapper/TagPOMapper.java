package cn.andylhl.crowd.mapper;

import cn.andylhl.crowd.po.TagPO;
import cn.andylhl.crowd.po.TagPOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TagPOMapper {
    int countByExample(TagPOExample example);

    int deleteByExample(TagPOExample example);

    int deleteByPrimaryKey(String id);

    int insert(TagPO record);

    int insertSelective(TagPO record);

    List<TagPO> selectByExample(TagPOExample example);

    TagPO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TagPO record, @Param("example") TagPOExample example);

    int updateByExample(@Param("record") TagPO record, @Param("example") TagPOExample example);

    int updateByPrimaryKeySelective(TagPO record);

    int updateByPrimaryKey(TagPO record);

    // 保存项目id与标签id之间的关系
    void saveTagList(@Param("tagMap") Map<String, String> tagMap, @Param("projectid") String projectid);
}