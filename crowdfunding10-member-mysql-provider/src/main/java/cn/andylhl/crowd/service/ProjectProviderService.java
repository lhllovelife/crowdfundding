package cn.andylhl.crowd.service;

import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.PortalTypeVO;
import cn.andylhl.crowd.vo.ProjectVO;

import java.util.List;

/***
 * @Title: ProjectService
 * @Description:
 * @author: lhl
 * @date: 2021/1/20 14:48
 */
public interface ProjectProviderService {


    /**
     * 将项目信息持久化到数据库
     * @param projectVO
     * @param memberid
     */
    void saveProjectVO(ProjectVO projectVO, String memberid);

    /**
     * 获取分类项目数据
     * @return
     */
    List<PortalTypeVO> getPrtalTypeProjectData();
}
