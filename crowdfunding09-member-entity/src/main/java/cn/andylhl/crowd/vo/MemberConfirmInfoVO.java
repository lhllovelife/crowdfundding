package cn.andylhl.crowd.vo;

import java.io.Serializable;

/***
 * @Title: MemberConfirmInfoVO
 * @Description: 发起人确认信息
 * @author: lhl
 * @date: 2021/1/18 15:20
 */
public class MemberConfirmInfoVO implements Serializable {

    // 易付宝账号
    private String paynum;

    // 法人身份证号
    private String cardnum;

    public MemberConfirmInfoVO() {
    }

    public MemberConfirmInfoVO(String paynum, String cardnum) {
        this.paynum = paynum;
        this.cardnum = cardnum;
    }

    public String getPaynum() {
        return paynum;
    }

    public void setPaynum(String paynum) {
        this.paynum = paynum;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    @Override
    public String toString() {
        return "MemberConfirmInfoVO{" +
                "paynum='" + paynum + '\'' +
                ", cardnum='" + cardnum + '\'' +
                '}';
    }
}
