package cn.andylhl.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.xml.soap.SAAJResult;

/***
 * @Title: CrowdWebMvcConfig
 * @Description:
 * @author: lhl
 * @date: 2021/1/15 10:30
 */

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        String urlPath = "/auth/member/to/reg/page.html";
        String viewName = "member-reg";
        // 添加ViewController
        registry.addViewController(urlPath).setViewName(viewName);
    }
}
