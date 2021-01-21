package cn.andylhl.crowd.vo;

/***
 * @Title: PortalProjectVO
 * @Description: 用于在页面上展示的项目相关数据
 * @author: lhl
 * @date: 2021/1/21 10:17
 */
public class PortalProjectVO {

    private String projectId;
    private String projectName;
    private String headerPicturePath;
    private Long money;
    private String deploydate;
    private String percentage;
    private Long supporter;

    public PortalProjectVO() {
    }

    public PortalProjectVO(String projectId, String projectName, String headerPicturePath, Long money, String deploydate, String percentage, Long supporter) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.headerPicturePath = headerPicturePath;
        this.money = money;
        this.deploydate = deploydate;
        this.percentage = percentage;
        this.supporter = supporter;
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

    public String getHeaderPicturePath() {
        return headerPicturePath;
    }

    public void setHeaderPicturePath(String headerPicturePath) {
        this.headerPicturePath = headerPicturePath;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getDeploydate() {
        return deploydate;
    }

    public void setDeploydate(String deploydate) {
        this.deploydate = deploydate;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public Long getSupporter() {
        return supporter;
    }

    public void setSupporter(Long supporter) {
        this.supporter = supporter;
    }

    @Override
    public String toString() {
        return "PortalProjectVO{" +
                "projectId='" + projectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", headerPicturePath='" + headerPicturePath + '\'' +
                ", money=" + money +
                ", deploydate='" + deploydate + '\'' +
                ", percentage='" + percentage + '\'' +
                ", supporter=" + supporter +
                '}';
    }
}
