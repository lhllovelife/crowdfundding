package cn.andylhl.crowd.vo;

import java.util.List;

/***
 * @Title: DetailProjectVO
 * @Description: 展示项目详细信息
 * @author: lhl
 * @date: 2021/1/23 11:39
 */
public class DetailProjectVO {

    // 项目id y
    private String projectId;
    //项目名称 y
    private String projectName;
    // 项目描述 y
    private String projectDesc;
    // 筹集金额 y
    private Integer money;
    // 筹集天数 y
    private Integer day;
    // 项目还剩多少天截止 (后期计算)
    private Integer lastDay;
    // 筹集状态编号 y
    private Integer status;
    // 筹集状态名称 （后期判断）
    private String statusText;
    // 关注人数 y
    private Integer followerCount;
    // 项目发起时间 y
    private String deployDate;

    // 已筹集到的金额 y
    private Integer supportMoney;
    // 百分比(已筹集到的金额 * 100 / supportMoney) y
    private Integer percentage;
    // 头图地址 y
    private String headerPicturePath;
    // 该项目总共支持数目 y
    private Integer supporterCount;

    // 详情图地址
    private List<String> detailPicturePathList;
    // 回报信息集合
    private List<DetailReturnVO> detailReturnVOList;

    public DetailProjectVO() {
    }

    public DetailProjectVO(String projectId, String projectName, String projectDesc, Integer money, Integer day, Integer lastDay, Integer status, String statusText, Integer followerCount, String deployDate, Integer supportMoney, Integer percentage, String headerPicturePath, Integer supporterCount, List<String> detailPicturePathList, List<DetailReturnVO> detailReturnVOList) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectDesc = projectDesc;
        this.money = money;
        this.day = day;
        this.lastDay = lastDay;
        this.status = status;
        this.statusText = statusText;
        this.followerCount = followerCount;
        this.deployDate = deployDate;
        this.supportMoney = supportMoney;
        this.percentage = percentage;
        this.headerPicturePath = headerPicturePath;
        this.supporterCount = supporterCount;
        this.detailPicturePathList = detailPicturePathList;
        this.detailReturnVOList = detailReturnVOList;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getLastDay() {
        return lastDay;
    }

    public void setLastDay(Integer lastDay) {
        this.lastDay = lastDay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public String getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(String deployDate) {
        this.deployDate = deployDate;
    }

    public Integer getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Integer supportMoney) {
        this.supportMoney = supportMoney;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath;
    }

    public Integer getSupporterCount() {
        return supporterCount;
    }

    public void setSupporterCount(Integer supporterCount) {
        this.supporterCount = supporterCount;
    }

    public List<String> getDetailPicturePathList() {
        return detailPicturePathList;
    }

    public void setDetailPicturePathList(List<String> detailPicturePathList) {
        this.detailPicturePathList = detailPicturePathList;
    }

    public List<DetailReturnVO> getDetailReturnVOList() {
        return detailReturnVOList;
    }

    public void setDetailReturnVOList(List<DetailReturnVO> detailReturnVOList) {
        this.detailReturnVOList = detailReturnVOList;
    }

    @Override
    public String toString() {
        return "DetailProjectVO{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectDesc='" + projectDesc + '\'' +
                ", money=" + money +
                ", day=" + day +
                ", lastDay=" + lastDay +
                ", status=" + status +
                ", statusText='" + statusText + '\'' +
                ", followerCount=" + followerCount +
                ", deployDate='" + deployDate + '\'' +
                ", supportMoney=" + supportMoney +
                ", percentage=" + percentage +
                ", headerPicturePath='" + headerPicturePath + '\'' +
                ", supporterCount=" + supporterCount +
                ", detailPicturePathList=" + detailPicturePathList +
                ", detailReturnVOList=" + detailReturnVOList +
                '}';
    }
}
