package cn.andylhl.crowd.po;

public class MemberConfirmInfoPo {
    private String id;

    private String memberid;

    private String paynum;

    private String cardnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid == null ? null : memberid.trim();
    }

    public String getPaynum() {
        return paynum;
    }

    public void setPaynum(String paynum) {
        this.paynum = paynum == null ? null : paynum.trim();
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }
}