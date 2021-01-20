package cn.andylhl.crowd.mapper;


import cn.andylhl.crowd.po.TypePO;
import cn.andylhl.crowd.po.TypePOExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TypePOMapper {
    int countByExample(TypePOExample example);

    int deleteByExample(TypePOExample example);

    int deleteByPrimaryKey(String id);

    int insert(TypePO record);

    int insertSelective(TypePO record);

    List<TypePO> selectByExample(TypePOExample example);

    TypePO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TypePO record, @Param("example") TypePOExample example);

    int updateByExample(@Param("record") TypePO record, @Param("example") TypePOExample example);

    int updateByPrimaryKeySelective(TypePO record);

    int updateByPrimaryKey(TypePO record);

    // 保存项目id与分类id之间的关系
    void saveTypeList(@Param("typeMap") Map<String, String> typeMap, @Param("projectid") String projectid);
}