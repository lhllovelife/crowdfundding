package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.api.MySQLRemoteService;
import cn.andylhl.crowd.config.OSSProperties;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * @Title: ProjectController
 * @Description: 项目相关控制器
 * @author: lhl
 * @date: 2021/1/18 15:04
 */

@Controller
public class ProjectController {

    private Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    /**
     * 保存项目及发起人信息
     * 主要接受的参数有项目的基本信息和项目发起人的信息
     * @return
     */
    @RequestMapping("/create/project/information")
    public String saveProjectBascicInfo(ProjectVO projectVO,
                                        MultipartFile headerPicture, // 接受上传的头图文件
                                        List<MultipartFile> detailPictureList, // 接收上传的详情图片
                                        Model model,
                                        HttpSession session) throws IOException {
        logger.info("crowd-project服务, 保存项目及发起人信息");
        logger.info(projectVO.toString());
        logger.info(headerPicture.toString());
        logger.info(detailPictureList.toString());

        // 1. 将头图上传到OSS

        // 检查头图是否为空
        if (headerPicture.isEmpty()) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_HEADPIC_IS_EMPTY);
            return "project-launch.html";
        }

        ResultEntity<String> headerPictureResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename());
        // 若头图上传失败，返回到原页面，并携带错误消息
        if (ResultEntity.FAILED.equals(headerPictureResultEntity.getResult())) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_HEADPIC_UPLOAD_FAILE);
            return "project-launch.html";
        }

        // 头图上传成功，将返回的头图地址设置到projectVO对象中
        String headerPicturePath = headerPictureResultEntity.getData();
        projectVO.setHeaderPicturePath(headerPicturePath);

        // 2. 循环上传详情图片

        // 若没有有效的详情图片，携带错误信息返回到提交页面
        if (detailPictureList == null || detailPictureList.size() == 0) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_DETAILPIC_IS_EMPTY);
            return "project-launch.html";
        }

        // 准备一个集合，存储详情图片的路径
        List<String> detailPicturePathList = new ArrayList<>();
        for (MultipartFile detailPicture : detailPictureList) {
            if (detailPicture.isEmpty()) {
                // 文件为空，携带错误信息返回到提交页面
                model.addAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_DETAILPIC_IS_EMPTY);
                return "project-launch.html";
            }
            // 上传详情图片
            ResultEntity<String> detailPictureResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    detailPicture.getOriginalFilename());
            // 若上传失败，返回表单页面，携带错误信息
            if (ResultEntity.FAILED.equals(detailPictureResultEntity.getResult())) {
                model.addAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_DETAILPIC_UPLOAD_FAILE);
                return "project-launch.html";
            }
            // 上传成功将详情图片地址存入集合
            detailPicturePathList.add(detailPictureResultEntity.getData());
        }

        // 将详情地址集合设置到projectVO对象中
        projectVO.setDetailPicturePathList(detailPicturePathList);

        // 3.将projectVO临时存储到session域对象中
        session.setAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
        // 重定向到收集回报信息页面
        return "redirect:http://127.0.0.1:80/project/return/info/page";
    }


    /**
     * 将回报信息图片上传到OSS，将上传后的地址返回给前端
     * MultipartFile 接收文件类型
     * @return
     */
    @RequestMapping("/create/upload/return/picture")
    public @ResponseBody ResultEntity<String> uploadReturnPicture(MultipartFile returnPicture) throws IOException {
        logger.info("crowd-project服务, 将回报信息图片上传到OSS");
        // 判断文件是否为null或为空
        if (returnPicture == null || returnPicture.isEmpty()) {
            return ResultEntity.failed(Constant.MESSAGE_RETURNPIC_IS_EMPTY);
        }
        // 上传到OSS
        ResultEntity<String> returnPictureResultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());
        logger.info("上传返回结果: " + returnPictureResultEntity);
        return returnPictureResultEntity;
    }


    /**
     * 保存一条回报信息
     * @return
     */
    @RequestMapping("/create/save/return")
    public @ResponseBody ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession session) {
        logger.info("crowd-project服务, 保存一条回报信息");
        logger.info("回报信息数据：" + returnVO.toString());
        //1.  从session中取出之前缓存的ProjectVO对象
        try {
            ProjectVO projectVO = (ProjectVO) session.getAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT);
            // 判断缓存的projectVO是否还存在
            if (projectVO == null) {
                return ResultEntity.failed(Constant.MESSAGE_TEMPLE_PROJECTVO_MISS);
            }
            // 取出projectVO中的list
            List<ReturnVO> returnVOList = projectVO.getReturnVOList();
            // 判断集合是否有效
            if (returnVOList == null) {
                returnVOList = new ArrayList<>();
                // 将初始化后的集合设置到projectVO中
                projectVO.setReturnVOList(returnVOList);
            }
            // 将收集过后的表单数据添加到集合中
            returnVOList.add(returnVO);

            // 将有数据变化的returnVOList重新存入session(持久化到redis)
            session.setAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT, projectVO);
            logger.info("加有回报信息的projectVo: " + projectVO);
            // 执行到这里未出现异常，说明执行成功
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }


    @RequestMapping("/create/save/confirm")
    public String saveConfirmInfo(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session, Model model) {
        logger.info("crowd-project服务, 保存确认信息");
        // 1. 从session域对象中取出之前缓存的ProjectVO对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT);

        // 若projectVO不存在
        if (projectVO == null) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_TEMPLE_PROJECTVO_MISS);
            return "project-confirm";
        }

        // 将确认信息对象设置到projectVO中
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);

        // 从session中取出当前登录的用户对象
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(Constant.ATTR_NAME_LOGIN_MEMBER);
        // 取出当前登录对象的id
        String memberid = memberLoginVO.getId();
        // 调用provider-mysql提供的方法，将提交的所有项目相关信息持久化到mysql数据库中
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveProjectVORemote(projectVO, memberid);
        if (ResultEntity.FAILED.equals(saveResultEntity.getResult())) {
            model.addAttribute(Constant.ATTR_ERROR_MESSAGE, saveResultEntity.getMessage());
            return "project-confirm";
        }

        // 保存成功则将临时projectVO对象从session中移除
        session.removeAttribute(Constant.ATTR_NAME_TEMPLE_PROJECT);

        // 重定向到最终完成的页面
        return "redirect:http://127.0.0.1:80/project/create/sucess";
    }

    @RequestMapping("/get/project/detail/{projectid}")
    public String getProjectDetail(@PathVariable("projectid") String projectid, Model model){
        logger.info("crowd-project服务, 根据项目id获取项目详细信息");

        // 1. 调用mysql服务查询项目详细信息
        ResultEntity<DetailProjectVO> projectDetailRemote = mySQLRemoteService.getProjectDetailRemote(projectid);
        // 若查询成功，将数据封装到request域对象
        if (ResultEntity.SUCCESS.equals(projectDetailRemote.getResult())) {
            DetailProjectVO detailProjectVO = projectDetailRemote.getData();
            model.addAttribute("detailProjectVO", detailProjectVO);
        }

        // 跳转页面
        return "project-show-detail";
    }

}
