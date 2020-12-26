package cn.andylhl.crowd.entity;

/***
 * @Title: Role
 * @Description: 角色实体类
 * @author: lhl
 * @date: 2020/12/26 16:17
 */
public class Role {

    private String id; // 主键id

    private String name; // 角色名称

    private String createTime; // 创建时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Role() {
    }

    public Role(String id, String name, String createTime) {
        this.id = id;
        this.name = name;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}