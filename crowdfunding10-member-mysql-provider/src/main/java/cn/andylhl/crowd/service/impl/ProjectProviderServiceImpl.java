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
import cn.andylhl.crowd.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.LocalDate;
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

    /**
     * 获取分类项目数据
     * @return
     */
    @Override
    public List<PortalTypeVO> getPrtalTypeProjectData() {
        // 获取所有分类数据
        List<PortalTypeVO> portalTypeVOList = projectPOMapper.getAllType();
        // 根据分类id查询该分类下所有的项目数据
        for (PortalTypeVO portalTypeVO : portalTypeVOList) {
            List<PortalProjectVO> portalProjectVOList = projectPOMapper.getAllProjectByTypeId(portalTypeVO.getId());
            // 将查询到的项目数据封装到该分类对象的属性中
            portalTypeVO.setPortalProjectVOList(portalProjectVOList);
        }
        return portalTypeVOList;
    }

    /**
     * 根据项目id,获取项目详细信息及其回报信息
     * @param projectId
     * @return
     */
    @Override
    public DetailProjectVO getProjectDetail(String projectId) {
        // 1. 准备要返回的数据
        DetailProjectVO detailProjectVO = new DetailProjectVO();
        // 2. 根据项目id查询，项目的详细信息
        detailProjectVO = projectPOMapper.getProjectInfo(projectId);
        // 根据status,确定statusText
        Integer status = detailProjectVO.getStatus();
        if (status == 0) {
            detailProjectVO.setStatusText("审核中");
        }
        else if (status == 1) {
            detailProjectVO.setStatusText("众筹中");
        }
        else if (status == 2) {
            detailProjectVO.setStatusText("众筹成功");
        }
        else if (status == 3) {
            detailProjectVO.setStatusText("已关闭");
        }
        // 根据发布日期确定，lastday
        // 发布日期
        String deployDate = detailProjectVO.getDeployDate();

        // 开始日期
        LocalDate startDate = LocalDate.parse(deployDate);
        // 结束日期
        LocalDate endDate = LocalDate.now();
        // 已经过去天数
        int passDay = (int) (endDate.toEpochDay() - startDate.toEpochDay());
        // 筹集天数
        Integer day = detailProjectVO.getDay();
        // 项目剩余天数
        Integer lastDay = day- passDay;
        detailProjectVO.setLastDay(lastDay);


        // 3. 根据项目id查询该项目的详情地址集合
        List<String> detailPicturePathList = projectPOMapper.getDetailPicturePathList(projectId);
        // 将查询到地址集合设置到detailProjectVO中
        detailProjectVO.setDetailPicturePathList(detailPicturePathList);
        // 4. 查询该项目的回报信息集合
        List<DetailReturnVO> detailReturnVOList = projectPOMapper.getDetailReturnVOList(projectId);
        // 遍历回报项目集合，查询每个档位回报各有多少人支持
        for (DetailReturnVO detailReturnVO : detailReturnVOList) {
            String returnId = detailReturnVO.getReturnId();
            // 根据returnId查询该档位的支持人数
            Integer supproterCount = projectPOMapper.getReturnSupportCount(returnId);
            detailReturnVO.setSupporterCount(supproterCount);
        }
        // 将回报信息数据设置到detailProjectVO
        detailProjectVO.setDetailReturnVOList(detailReturnVOList);

        return detailProjectVO;
    }
}
/*

DetailProjectVO{
projectId='aa918f862bb44642a7c5832cc5a54435',
projectName='iphone12',
projectDesc='iPhone 12是苹果公司旗下的智能手机，尺寸为6.1英寸，采用了Super Retina XDR屏幕。',
money=7499,
day=30,
lastDay=null,
 status=0,
  statusText='null',
  followerCount=null,
  deployDate='null',
   supportMoney=null,
    percentage=null,
    headerPicturePath='http://andylhlcrowd.oss-cn-beijing.aliyuncs.com/20210123/2264a864969d4eab9d12f7d6e5bd670c.jfif',
     supporterCount=null,
     detailPicturePathList=null,
      detailReturnVOList=null}
 */