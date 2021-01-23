package cn.andylhl.crowd.mapper;


import java.util.List;

import cn.andylhl.crowd.po.ReturnSupportPO;
import cn.andylhl.crowd.po.ReturnSupportPOExample;
import org.apache.ibatis.annotations.Param;

public interface ReturnSupportPOMapper {
    int countByExample(ReturnSupportPOExample example);

    int deleteByExample(ReturnSupportPOExample example);

    int deleteByPrimaryKey(String id);

    int insert(ReturnSupportPO record);

    int insertSelective(ReturnSupportPO record);

    List<ReturnSupportPO> selectByExample(ReturnSupportPOExample example);

    ReturnSupportPO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ReturnSupportPO record, @Param("example") ReturnSupportPOExample example);

    int updateByExample(@Param("record") ReturnSupportPO record, @Param("example") ReturnSupportPOExample example);

    int updateByPrimaryKeySelective(ReturnSupportPO record);

    int updateByPrimaryKey(ReturnSupportPO record);
}