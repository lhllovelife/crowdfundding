package cn.andylhl.crowd.constant;

import java.util.HashSet;
import java.util.Set;

/***
 * @Title: AccessPassResources
 * @Description: 可放行的资源
 * @author: lhl
 * @date: 2021/1/17 19:00
 */
public class AccessPassResources {

    public static final Set<String> PASS_RES_SET = new HashSet<>();

    // 当类加载时候，执行该静态代码块
    static {

        // 网站门户
        PASS_RES_SET.add("/");
        // 注册页面
        PASS_RES_SET.add("/auth/member/to/reg/page");
        // 注册接口
        PASS_RES_SET.add("/auth/do/member/register");
        // 获取验证码接口
        PASS_RES_SET.add("/auth/member/send/short/message");
        // 登录页面
        PASS_RES_SET.add("/auth/member/to/login/page");
        // 登录接口
        PASS_RES_SET.add("/auth/member/do/login");
        // 登出接口
        PASS_RES_SET.add("/auth/member/do/logout");
    }

    public static final Set<String> STATIC_RES_SET = new HashSet<>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 判断当前访问地址是属于"放行地址"
     * @param servletPath
     * @return true代表可放行，false代表不可放行
     */
    public static Boolean judgeCurrentServletPathWhetherStaticResource (String servletPath ) {

        if (servletPath == null || "".equals(servletPath)) {
            return false;
        }

        if (PASS_RES_SET.contains(servletPath)) {
            // 属于可放行资源
            return true;
        }
        // 将地址以"/"为分割符号进行拆分
        String[] stringArray = servletPath.split("/");
        String indexString = stringArray[1];
        if (STATIC_RES_SET.contains(indexString)) {
            return true;
        }
        return false;
    }
}
