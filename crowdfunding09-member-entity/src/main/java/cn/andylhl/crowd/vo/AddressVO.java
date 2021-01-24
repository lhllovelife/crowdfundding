package cn.andylhl.crowd.vo;

/***
 * @Title: AddressVO
 * @Description: 地址信息数据
 * @author: lhl
 * @date: 2021/1/24 18:58
 */
public class AddressVO {

    // 主键
    private String id;

    // 收件人
    private String receiveName;

    // 手机号
    private String phoneNum;

    // 收货地址
    private String address;

    // 用户 id
    private String memberId;

    public AddressVO() {
    }

    public AddressVO(String id, String receiveName, String phoneNum, String address, String memberId) {
        this.id = id;
        this.receiveName = receiveName;
        this.phoneNum = phoneNum;
        this.address = address;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "AddressVO{" +
                "id='" + id + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", address='" + address + '\'' +
                ", memberId='" + memberId + '\'' +
                '}';
    }
}
