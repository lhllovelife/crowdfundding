package cn.andylhl.crowd.mapper;


import cn.andylhl.crowd.po.AddressPO;
import cn.andylhl.crowd.po.AddressPOExample;
import cn.andylhl.crowd.vo.AddressVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressPOMapper {
    int countByExample(AddressPOExample example);

    int deleteByExample(AddressPOExample example);

    int deleteByPrimaryKey(String id);

    int insert(AddressPO record);

    int insertSelective(AddressPO record);

    List<AddressPO> selectByExample(AddressPOExample example);

    AddressPO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") AddressPO record, @Param("example") AddressPOExample example);

    int updateByExample(@Param("record") AddressPO record, @Param("example") AddressPOExample example);

    int updateByPrimaryKeySelective(AddressPO record);

    int updateByPrimaryKey(AddressPO record);

    // 根据memberId查询地址信息
    List<AddressVO> getAddressList(String memberId);
}