package cn.andylhl.crowd.service;

import cn.andylhl.crowd.utils.ResultEntity;
import cn.andylhl.crowd.vo.ProjectVO;

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
}
