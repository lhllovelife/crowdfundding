package cn.andylhl.crowd.vo;

/***
 * @Title: DetailReturnVO
 * @Description: 展示项目回报信息
 * @author: lhl
 * @date: 2021/1/23 11:39
 */
public class DetailReturnVO {

    // 回报信息主键 y
    private String returnId;

    // 当前档位需要支持的金额 y
    private Long supportMoney;

    // 回报内容 y
    private String content;

    // 回报产品限额，“0”为不限回报数量 y
    private Integer count;

    // 单笔限购，取值为 0 时无限额，取值为 1 时有限额 y
    private Integer signalPurchase;

    // 具体单笔限购限额数量 y
    private Integer purchase;

    // 当前该档位支持者数量（根据t_return_support去查询）
    private Integer supporterCount;

    // 运费，取值为 0 时表示包邮 y
    private Integer freight;

    // 众筹成功后多少天发货 y
    private Integer returnDate;

    public DetailReturnVO() {
    }

    public DetailReturnVO(String returnId, Long supportMoney, String content, Integer count, Integer signalPurchase, Integer purchase, Integer supporterCount, Integer freight, Integer returnDate) {
        this.returnId = returnId;
        this.supportMoney = supportMoney;
        this.content = content;
        this.count = count;
        this.signalPurchase = signalPurchase;
        this.purchase = purchase;
        this.supporterCount = supporterCount;
        this.freight = freight;
        this.returnDate = returnDate;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
    }

    public Long getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Long supportMoney) {
        this.supportMoney = supportMoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Integer getSupporterCount() {
        return supporterCount;
    }

    public void setSupporterCount(Integer supporterCount) {
        this.supporterCount = supporterCount;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Integer returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "DetailReturnVO{" +
                "returnId='" + returnId + '\'' +
                ", supportMoney=" + supportMoney +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", signalPurchase=" + signalPurchase +
                ", purchase=" + purchase +
                ", supporterCount=" + supporterCount +
                ", freight=" + freight +
                ", returnDate=" + returnDate +
                '}';
    }
}
