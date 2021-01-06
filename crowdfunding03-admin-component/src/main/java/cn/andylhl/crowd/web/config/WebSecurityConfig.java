package cn.andylhl.crowd.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/***
 * @Title: WebSecurityConfig
 * @Description: security配置
 * @author: lhl
 * @date: 2021/1/6 10:25
 */

@Configuration // 声明该类为一个配置类
@EnableWebSecurity // 启用spring secuirty配置
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

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
                ;
    }
}
