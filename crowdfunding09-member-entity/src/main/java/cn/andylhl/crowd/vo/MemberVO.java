package cn.andylhl.crowd.vo;

import java.util.stream.Stream;

/***
 * @Title: MemberVO
 * @Description: 会员注册表单提交数据
 * @author: lhl
 * @date: 2021/1/15 22:48
 */
public class MemberVO {

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String phoneNum;

    private String verifyCode;

    public MemberVO() {
    }

    public MemberVO(String loginacct, String userpswd, String username, String email, String phoneNum, String verifyCode) {
        this.loginacct = loginacct;
        this.userpswd = userpswd;
        this.username = username;
        this.email = email;
        this.phoneNum = phoneNum;
        this.verifyCode = verifyCode;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "loginacct='" + loginacct + '\'' +
                ", userpswd='" + userpswd + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}

/*
loginacct: fe
userpswd: fe
username: fe
email: fengqingyang@.com
phoneNum: fe
verifyCode: fe
 */
