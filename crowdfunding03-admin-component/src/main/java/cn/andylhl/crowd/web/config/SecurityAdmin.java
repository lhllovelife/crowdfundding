package cn.andylhl.crowd.web.config;

import cn.andylhl.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/***
 * @Title: SecurityAdmin
 * @Description:
 * @author: lhl
 * @date: 2021/1/7 15:23
 */
public class SecurityAdmin extends User {

    // 原始的Admin对象，保存Admin对象的全部属性
    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {

        // 调用父类构造器
        super(originalAdmin.getLoginAcct(), originalAdmin.getUserPswd(), authorities);

        // 给本类originalAdmin赋值
        this.originalAdmin = originalAdmin;

        // 将originalAdmin中的密码擦除
        this.originalAdmin.setUserPswd("");
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }

    public void setOriginalAdmin(Admin originalAdmin) {
        this.originalAdmin = originalAdmin;
    }

    @Override
    public String toString() {
        return "SecurityAdmin{" +
                "originalAdmin=" + originalAdmin +
                '}';
    }
}
