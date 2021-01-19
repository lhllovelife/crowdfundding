package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.config.OSSProperties;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
