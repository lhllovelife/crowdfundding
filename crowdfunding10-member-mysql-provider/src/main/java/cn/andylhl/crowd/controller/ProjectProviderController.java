package cn.andylhl.crowd.controller;

import cn.andylhl.crowd.service.ProjectProviderService;
import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.ProjectVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
