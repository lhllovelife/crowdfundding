package cn.andylhl.crowd.vo;

/***
 * @Title: MemberLoginVO
 * @Description: 用于存储登录用户信息，放置到session域对象中
 * @author: lhl
 * @date: 2021/1/16 17:33
 */
public class MemberLoginVO {

    private String id;

    private String username;

    private String email;

    public MemberLoginVO() {
    }

    public MemberLoginVO(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "MemberLoginVO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
