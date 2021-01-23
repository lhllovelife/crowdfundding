package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.service.ProjectProviderService;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.DetailProjectVO;
import cn.andylhl.crowd.vo.PortalTypeVO;
import cn.andylhl.crowd.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * @Title: ProjectProviderController
 * @Description: 项目相关数据服务
 * @author: lhl
 * @date: 2021/1/19 14:41
 */

@RestController
public class ProjectProviderController {

    Logger logger = LoggerFactory.getLogger(ProjectProviderController.class);

    @Autowired
    private ProjectProviderService projectProviderService;

    /**
     * 将项目信息持久化到数据库
     * @param projectVO
     * @param memberid
     * @return
     */
    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberid") String memberid) {
        logger.info("mysql-provider服务，将项目信息持久化到数据库");
        logger.info(projectVO.toString());
        logger.info(memberid);

        try {
            projectProviderService.saveProjectVO(projectVO, memberid);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    /**
     * 获取分类项目数据
     * @return
     */
    @RequestMapping("/get/portal/type/project/data/remote")
    public ResultEntity<List<PortalTypeVO>> getPrtalTypeProjectDataRemote() {
        logger.info("mysql-provider服务，获取分类项目数据");

        try {
            List<PortalTypeVO> portalTypeVOList = projectProviderService.getPrtalTypeProjectData();
            return ResultEntity.successWithData(portalTypeVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    /**
     * 根据项目id,获取项目详细信息及其回报信息
     * @param projectId
     * @return
     */
    @RequestMapping("/get/project/detail/remote")
    public ResultEntity<DetailProjectVO> getProjectDetailRemote(@RequestParam("projectId") String projectId) {
        logger.info("mysql-provider服务，根据项目id,获取项目详细信息及其回报信息");

        try {
            DetailProjectVO detailProjectVO = projectProviderService.getProjectDetail(projectId);
            return ResultEntity.successWithData(detailProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

}
