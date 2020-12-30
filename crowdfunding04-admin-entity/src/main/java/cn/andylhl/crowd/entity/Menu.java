package cn.andylhl.crowd.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private Integer id; // 主键id(int 自增)

    private Integer pid; //  父节点的id

    private String name; // 当前节点的名称

    private String url; // 点击当前节点要跳转的位置

    private String icon; // 当前节点使用的图标

    private Boolean open = true; // 控制节点是否默认打开，默认为true，是打开的

    private List<Menu> children = new ArrayList<>(); // 存储当前节点的子节点，为了避免空指针异常，直接new出集合

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", open=" + open +
                ", children=" + children +
                '}';
    }
}