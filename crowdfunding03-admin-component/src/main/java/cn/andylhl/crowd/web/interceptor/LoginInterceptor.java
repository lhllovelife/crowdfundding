package cn.andylhl.crowd.web.interceptor;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.entity.Admin;
import cn.andylhl.crowd.exception.AccessForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/***
 * @Title: LoginInterceptor
 * @Description: 登录检查。
 * 将部分资源保护起来，没有登录则不能进行访问，登录后才能进行访问。
 * @author: lhl
 * @date: 2020/12/23 20:53
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取session对象
        HttpSession session = request.getSession();
        //2. 从session对象中取 管理员对象
        Admin admin = (Admin) session.getAttribute(Constant.ATTR_NAME_LOGIN_ADMIN);
        //3. 判断session域中是否有管理员对象
        if (admin == null){
            logger.info("访问受限资源，请登录后再访问");
            //抛出异常后，由异常处理机制统一处理
            throw new AccessForbiddenException("该资源受保护，请登录后再访问");
        }

        return true;
    }
}
