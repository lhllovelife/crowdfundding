package cn.andylhl.crowd.vo;

import java.io.Serializable;

/***
 * @Title: OrderVO
 * @Description:
 * @author: lhl
 * @date: 2021/1/24 22:25
 */
public class OrderVO implements Serializable {

    // 主键
    private String id;

    // 订单号(商家生成的)
    private String orderNum;

    // 支付宝流水单号
    private String payOrderNum;

    // 订单金额
    private Double orderAmount;

    // 是否开发票 y
    private Integer invoice;

    // 发票抬头 y
    private String invoiceTitle;

    // 备注 y
    private String orderRemark;

    // 地址id y
    private String addressId;

    // 已组装
    private OrderProjectVO orderProjectVO;


    public OrderVO() {
    }

    public OrderVO(String id, String orderNum, String payOrderNum, Double orderAmount, Integer invoice, String invoiceTitle, String orderRemark, String addressId, OrderProjectVO orderProjectVO) {
        this.id = id;
        this.orderNum = orderNum;
        this.payOrderNum = payOrderNum;
        this.orderAmount = orderAmount;
        this.invoice = invoice;
        this.invoiceTitle = invoiceTitle;
        this.orderRemark = orderRemark;
        this.addressId = addressId;
        this.orderProjectVO = orderProjectVO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(String payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getInvoice() {
        return invoice;
    }

    public void setInvoice(Integer invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public OrderProjectVO getOrderProjectVO() {
        return orderProjectVO;
    }

    public void setOrderProjectVO(OrderProjectVO orderProjectVO) {
        this.orderProjectVO = orderProjectVO;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id='" + id + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", payOrderNum='" + payOrderNum + '\'' +
                ", orderAmount=" + orderAmount +
                ", invoice=" + invoice +
                ", invoiceTitle='" + invoiceTitle + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", addressId='" + addressId + '\'' +
                ", orderProjectVO=" + orderProjectVO +
                '}';
    }
}
