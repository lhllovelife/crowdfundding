package cn.andylhl.crowd.mapper;

import java.util.List;

import cn.andylhl.crowd.po.MemberConfirmInPo;
import cn.andylhl.crowd.po.MemberConfirmInPoExample;
import org.apache.ibatis.annotations.Param;

public interface MemberConfirmInPoMapper {
    int countByExample(MemberConfirmInPoExample example);

    int deleteByExample(MemberConfirmInPoExample example);

    int deleteByPrimaryKey(String id);

    int insert(MemberConfirmInPo record);

    int insertSelective(MemberConfirmInPo record);

    List<MemberConfirmInPo> selectByExample(MemberConfirmInPoExample example);

    MemberConfirmInPo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MemberConfirmInPo record, @Param("example") MemberConfirmInPoExample example);

    int updateByExample(@Param("record") MemberConfirmInPo record, @Param("example") MemberConfirmInPoExample example);

    int updateByPrimaryKeySelective(MemberConfirmInPo record);

    int updateByPrimaryKey(MemberConfirmInPo record);
}