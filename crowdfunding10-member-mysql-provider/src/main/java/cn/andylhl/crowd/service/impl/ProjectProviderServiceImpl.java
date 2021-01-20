package cn.andylhl.crowd.service.impl;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.mapper.*;
import cn.andylhl.crowd.po.MemberConfirmInfoPo;
import cn.andylhl.crowd.po.MemberLaunchInfoPO;
import cn.andylhl.crowd.po.ProjectPO;
import cn.andylhl.crowd.po.ReturnPO;
import cn.andylhl.crowd.service.ProjectProviderService;
import cn.andylhl.crowd.utils.DateUtil;
import cn.andylhl.crowd.utils.UUIDUtil;
import cn.andylhl.crowd.vo.MemberConfirmInfoVO;
import cn.andylhl.crowd.vo.MemberLauchInfoVO;
import cn.andylhl.crowd.vo.ProjectVO;
import cn.andylhl.crowd.vo.ReturnVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/***
 * @Title: ProjectProviderServiceImpl
 * @Description:
 * @author: lhl
 * @date: 2021/1/20 14:49
 */

@Transactional(readOnly = true)
@Service
public class ProjectProviderServiceImpl implements ProjectProviderService {

    Logger logger = LoggerFactory.getLogger(ProjectProviderServiceImpl.class);

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Autowired
    private TypePOMapper typePOMapper;

    @Autowired
    private TagPOMapper tagPOMapper;

    @Autowired
    private ProjectItemPicPOMapper projectItemPicPOMapper;

    @Autowired
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

    @Autowired
    private ReturnPOMapper returnPOMapper;

    @Autowired
    private MemberConfirmInfoPoMapper memberConfirmInfoPoMapper;

    /**
     * 将项目信息持久化到数据库
     * @param projectVO
     * @param memberid
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = false)
    @Override
    public void saveProjectVO(ProjectVO projectVO, String memberid) {

        // 1. 保存基本项目信息, 准备ProjectPO
        ProjectPO projectPO = new ProjectPO();
        // 将属性复制到PO对象中
        BeanUtils.copyProperties(projectVO, projectPO);
        // 准备项目的uuid
        String projectid = UUIDUtil.getUUID();
        projectPO.setId(projectid);
        // 项目的创建时间
        projectPO.setCreatedate(DateUtil.format(new Date(), Constant.DATE_Format_ALL));
        // 项目的创建人memberid
        projectPO.setMemberid(memberid);
        // 项目目前状态
        projectPO.setStatus(0);
        // 调用ProjectPOMapper执行保存
        logger.info("执行保存的projectPO: " + projectPO);
        projectPOMapper.insert(projectPO);

        // 2. 保存项目分类中间表 typeIdList （一对多）
        List<String> typeIdList = projectVO.getTypeIdList();
        // 准备要存储的信息(为每个typeId准备对应的uuid)
        Map<String, String> typeMap = new HashMap<>();
        for (String typeId : typeIdList) {
            typeMap.put(UUIDUtil.getUUID(), typeId);
        }
        // 保存项目id与分类id之间的关系
        typePOMapper.saveTypeList(typeMap, projectid);

        // 3. 保存项目标签中间表 tagIdList （一对多）
        List<String> tagIdList = projectVO.getTagIdList();
        // 装备要存储的标签信息
        Map<String, String> tagMap = new HashMap<>();
        for (String tagId : tagIdList) {
            tagMap.put(UUIDUtil.getUUID(), tagId);
        }
        // 保存项目id与标签id之间的关系
        tagPOMapper.saveTagList(tagMap, projectid);

        // 4. 保存项目详情图片表 detailPicturePathList（一对多）
        List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
        // 准备每个地址对应的uuid
        Map<String, String> detailPicturePathMap = new HashMap<>();
        for (String detailPicturePath : detailPicturePathList) {
            detailPicturePathMap.put(UUIDUtil.getUUID(), detailPicturePath);
        }
        //保存项目id与详情图片id之间的关系
        projectItemPicPOMapper.saveDetailPicturePathList(detailPicturePathMap, projectid);

        // 5. 保存项目发起人信息表 memberLauchInfoVO
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        // 准备项目发起人PO对象
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        // 属性复制
        BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);
        // 设置项目发起人uuid
        memberLaunchInfoPO.setId(UUIDUtil.getUUID());
        // 设置会员memberid
        memberLaunchInfoPO.setMemberid(memberid);
        // 执行保存
        memberLaunchInfoPOMapper.insertSelective(memberLaunchInfoPO);

        // 6. 保存回报信息表 （一对多） returnVOList
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        // 准备持久化集合对象
        List<ReturnPO> returnPOList = new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            // new一个returnPO
            ReturnPO returnPO = new ReturnPO();
            // 属性复制
            BeanUtils.copyProperties(returnVO, returnPO);
            // 设置uuid和projectid
            returnPO.setId(UUIDUtil.getUUID());
            returnPO.setProjectid(projectid);
            // 将returnPO添加到集合中
            returnPOList.add(returnPO);
        }
        // 执行保存回报信息
        returnPOMapper.saveReturnPOList(returnPOList);

        // 7. 保存发起人确认信息表 memberConfirmInfoVO
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        // 准备发起人确认信息持久化对象
        MemberConfirmInfoPo memberConfirmInfoPo = new MemberConfirmInfoPo();
        // 属性复制
        BeanUtils.copyProperties(memberConfirmInfoVO, memberConfirmInfoPo);
        // 准备uuid
        memberConfirmInfoPo.setId(UUIDUtil.getUUID());
        // 准备memberid
        memberConfirmInfoPo.setMemberid(memberid);
        // 保存发起人确认信息
        memberConfirmInfoPoMapper.saveMemberConfirmInfoPo(memberConfirmInfoPo);
    }
}

/*
ProjectVO{
typeIdList=[type1111, type2222],
tagIdList=[tag001, tag002, tag003],
projectName='麦克风',
projectDescription='麦克风一句话简介',
money=1500,
day=12,
createdate='null',
headerPicturePath='http://andylhlcrowd.oss-cn-beijing.aliyuncs.com/20210120/5a22074b02de43e3b523cf172437bca8.jpg',
detailPicturePathList=[http://andylhlcrowd.oss-cn-beijing.aliyuncs.com/20210120/d3cb50af6cdb462f81f5b8a6b5e353d9.gif, http://andylhlcrowd.oss-cn-beijing.aliyuncs.com/20210120/2bdbacc29ea64b9ebdf9ddd944c102fc.jpg],
memberLauchInfoVO=MemberLauchInfoVO{descriptionSimple='李红亮', descriptionDetail='李红亮详细自我介绍', phoneNum='15565615212', serviceNum='4000800123'},
returnVOList=[
ReturnVO{type=0, supportmoney=2, content='回报内容1', count=0, signalpurchase=0, purchase=1, freight=0, invoice=0, returndate=10, describPicPath='http://andylhlcrowd.oss-cn-beijing.aliyuncs.com/20210120/a5d5f4698840413581ea2061c3bcf00d.gif'},
ReturnVO{type=1, supportmoney=5, content='回报内容2', count=1, signalpurchase=1, purchase=15, freight=10, invoice=1, returndate=15, describPicPath='http://andylhlcrowd.oss-cn-beijing.aliyuncs.com/20210120/62b1c5b4783e4189b0b86a549655bf5c.jpg'}],
memberConfirmInfoVO=MemberConfirmInfoVO{paynum='2432707158', cardnum='412824200004017213'}}
 */