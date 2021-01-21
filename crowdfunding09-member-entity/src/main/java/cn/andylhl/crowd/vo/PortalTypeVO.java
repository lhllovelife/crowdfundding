package cn.andylhl.crowd.vo;

import java.util.List;

/***
 * @Title: PortalTypeVO
 * @Description: 分类数据
 * @author: lhl
 * @date: 2021/1/21 10:14
 */
public class PortalTypeVO {

    private String id; // 分类id
    private String name; // 分类名称
    private String remark; // 分类介绍

    private List<PortalProjectVO> portalProjectVOList; // 该分类下所有的项目集合

    public PortalTypeVO() {
    }

    public PortalTypeVO(String id, String name, String remark, List<PortalProjectVO> portalProjectVOList) {
        this.id = id;
        this.name = name;
        this.remark = remark;
        this.portalProjectVOList = portalProjectVOList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PortalProjectVO> getPortalProjectVOList() {
        return portalProjectVOList;
    }

    public void setPortalProjectVOList(List<PortalProjectVO> portalProjectVOList) {
        this.portalProjectVOList = portalProjectVOList;
    }

    @Override
    public String toString() {
        return "PortalTypeVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", portalProjectVOList=" + portalProjectVOList +
                '}';
    }
}
