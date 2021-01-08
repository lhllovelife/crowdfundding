package cn.andylhl.crowd.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/***
 * @Title: WebSecurityConfig
 * @Description: security配置
 * @author: lhl
 * @date: 2021/1/6 10:25
 */

@Configuration // 声明该类为一个配置类
@EnableWebSecurity // 启用spring secuirty配置
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用全局方法权限控制功能
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CrowdUserDetailService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.
//                inMemoryAuthentication().withUser("tom")
//                .password("123456").roles("ADMIN");
        System.out.println("----------->" + passwordEncoder);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security
                .authorizeRequests() // 对请求进行授权
                .antMatchers("/admin/to/login/page.html") // 对登录页面进行放行
                .permitAll()
                .antMatchers("/static/**") // 放行静态资源
                .permitAll()
                .anyRequest() // 其他请求
                .authenticated()  // 认证后访问
                .and()
                .csrf()
                .disable() // 禁用csrf功能
                .formLogin() // 开启表单提交功能
                .loginPage("/admin/to/login/page.html") // 指定登录界面
                .loginProcessingUrl("/security/do/login.html") // 处理登录请求的接口地址
                .defaultSuccessUrl("/admin/to/main/page.html") // 登录成功跳转到后台主页
                .usernameParameter("loginAcct") // 指定账号参数名称
                .passwordParameter("userPswd") // 指定密码参数名称
                .and()
                .logout()
                .logoutUrl("/security/do/logout.html") // 处理登出的接口地址
                .logoutSuccessUrl("/admin/to/login/page.html") // 登出成功后跳转到的地址
                ;
    }
}
