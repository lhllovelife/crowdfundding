package cn.andylhl.crowd.mapper;


import cn.andylhl.crowd.po.MemberConfirmInfoPo;
import cn.andylhl.crowd.po.MemberConfirmInfoPoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberConfirmInfoPoMapper {
    int countByExample(MemberConfirmInfoPoExample example);

    int deleteByExample(MemberConfirmInfoPoExample example);

    int deleteByPrimaryKey(String id);

    int insert(MemberConfirmInfoPo record);

    int insertSelective(MemberConfirmInfoPo record);

    List<MemberConfirmInfoPo> selectByExample(MemberConfirmInfoPoExample example);

    MemberConfirmInfoPo selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MemberConfirmInfoPo record, @Param("example") MemberConfirmInfoPoExample example);

    int updateByExample(@Param("record") MemberConfirmInfoPo record, @Param("example") MemberConfirmInfoPoExample example);

    int updateByPrimaryKeySelective(MemberConfirmInfoPo record);

    int updateByPrimaryKey(MemberConfirmInfoPo record);

    // 保存发起人确认信息
    void saveMemberConfirmInfoPo(MemberConfirmInfoPo memberConfirmInfoPo);
}