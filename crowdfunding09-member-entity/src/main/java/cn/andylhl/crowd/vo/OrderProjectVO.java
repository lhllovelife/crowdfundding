package cn.andylhl.crowd.vo;

import java.io.Serializable;

/***
 * @Title: OrderProjectVO
 * @Description: 订单项目信息
 * @author: lhl
 * @date: 2021/1/23 18:07
 */
public class OrderProjectVO implements Serializable {

    // 订单项目信息id (存入表中的时候再生成)
    private String id;

    // 项目名称 y
    private String projectName;

    // 发起人 launch_name y
    private String launchName;

    // 回报内容 y
    private String returnContent;

    // 回报数量 （由用户去设置）
    private Integer returnCount;

    // 该回报产品的限额数量 count y
    private Integer count;

    // 该回报产品剩余数目(之后计算) y
    private Integer remainCount;

    // 支持单价 y
    private Integer supportPrice;

    // 配送费用 y
    private Integer freight;

    // 订单表的主键 （用的时候生成）
    private Integer orderId;

    // 是否单笔限购 y
    private Integer signalPurchase;

    // 每人限购数量
    private Integer purchase;

    public OrderProjectVO() {
    }

    public OrderProjectVO(String id, String projectName, String launchName, String returnContent, Integer returnCount, Integer count, Integer remainCount, Integer supportPrice, Integer freight, Integer orderId, Integer signalPurchase, Integer purchase) {
        this.id = id;
        this.projectName = projectName;
        this.launchName = launchName;
        this.returnContent = returnContent;
        this.returnCount = returnCount;
        this.count = count;
        this.remainCount = remainCount;
        this.supportPrice = supportPrice;
        this.freight = freight;
        this.orderId = orderId;
        this.signalPurchase = signalPurchase;
        this.purchase = purchase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLaunchName() {
        return launchName;
    }

    public void setLaunchName(String launchName) {
        this.launchName = launchName;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
    }

    public Integer getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(Integer returnCount) {
        this.returnCount = returnCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(Integer remainCount) {
        this.remainCount = remainCount;
    }

    public Integer getSupportPrice() {
        return supportPrice;
    }

    public void setSupportPrice(Integer supportPrice) {
        this.supportPrice = supportPrice;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSignalPurchase() {
        return signalPurchase;
    }

    public void setSignalPurchase(Integer signalPurchase) {
        this.signalPurchase = signalPurchase;
    }

    public Integer getPurchase() {
        return purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "OrderProjectVO{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", launchName='" + launchName + '\'' +
                ", returnContent='" + returnContent + '\'' +
                ", returnCount=" + returnCount +
                ", count=" + count +
                ", remainCount=" + remainCount +
                ", supportPrice=" + supportPrice +
                ", freight=" + freight +
                ", orderId=" + orderId +
                ", signalPurchase=" + signalPurchase +
                ", purchase=" + purchase +
                '}';
    }
}
