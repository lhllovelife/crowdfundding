package cn.andylhl.crowd.filter;

import cn.andylhl.crowd.constant.AccessPassResources;
import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.vo.MemberLoginVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/***
 * @Title: CrowdAccessFilter
 * @Description:
 * @author: lhl
 * @date: 2021/1/17 20:27
 */
@Component
public class CrowdAccessFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(CrowdAccessFilter.class);

    @Override
    public boolean shouldFilter() {

        // 1. 获取RequestContext对象

        // 底层框架将当前context对象与当前线程绑定（使用ThreadLocal）
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        // 2. 获取当前请求路径
        String servletPath = request.getServletPath();
        logger.info("请求路径: " + servletPath);

        // 3. 判断当前请求是否可以放行
        Boolean tag = AccessPassResources.judgeCurrentServletPathWhetherStaticResource(servletPath);
        logger.info(tag == true ? "放行" : "不放行，进行登录检查");

        return !tag;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpSession session = request.getSession();
        // 尝试从session对象中取出登录的用户信息 (loginMember)
        // 放置到session中的对象需要序列化(在网络中传输)
        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(Constant.ATTR_NAME_LOGIN_MEMBER);

        // 判断是否存在登录信息
        if (memberLoginVO == null) {
             // 未登录，则重定向auth-consumer的登录页面
            // 1. 取出response对象
            HttpServletResponse response = context.getResponse();

            // 2. 将提示信息存入session域对象中，到登录页面显示
            // 因为是重定向，没法携带参数。
            // 当再次从浏览器到登录页面时候，会把cookie给带上，所以能从对应的session取数据
            session.setAttribute(Constant.ATTR_ERROR_MESSAGE, Constant.MESSAGE_ACCESS_FORBIDDEN);
            // 3. 重定向到auth-consumer登录界面
            try {
                // 由zull工程出发去重定向
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

}
