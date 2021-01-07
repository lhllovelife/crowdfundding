package cn.andylhl.crowd.web.config;

import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.entity.Role;
import cn.andylhl.crowd.service.AdminService;
import cn.andylhl.crowd.service.AuthService;
import cn.andylhl.crowd.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/***
 * @Title: UserDetailService
 * @Description:
 * @author: lhl
 * @date: 2021/1/7 14:20
 */
public class CrowdUserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(CrowdUserDetailService.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {

        logger.info("根据用户名查询用户信息：" + loginAcct);

        // 1. 根据用户名查询用户对象信息
        Admin admin = adminService.getAdminByLoginAcct(loginAcct);
        // 2. 根据adminId查询已分配角色
        List<Role> roleList = roleService.getAssignedRole(admin.getId());

        // 3. 根据adminId查询已分配权限的名称集合
        List<String> authNameList = authService.getAssignedAuthNameListByAdminId(admin.getId());

        // 4. 创建集合对象用来存储GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 5. 存入角色信息
        for (Role role : roleList) {
            logger.info("存入角色信息: " + "ROLE_" + role.getName());
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        // 6. 存入权限信息
        for (String authName : authNameList) {
            logger.info("存入权限信息: " + authName);
            authorities.add(new SimpleGrantedAuthority(authName));
        }

        // 7. 封装SecurityAdmin对象
        SecurityAdmin securityAdmin = new SecurityAdmin(admin, authorities);
        logger.info(securityAdmin.toString());
        return securityAdmin;
    }
}
