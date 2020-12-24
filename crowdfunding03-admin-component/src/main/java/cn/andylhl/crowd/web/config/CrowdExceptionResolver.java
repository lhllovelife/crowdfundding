package cn.andylhl.crowd.web.config;

import cn.andylhl.crowd.constant.Constant;
import cn.andylhl.crowd.exception.AccessForbiddenException;
import cn.andylhl.crowd.exception.LoginFailedException;
import cn.andylhl.crowd.utils.CrowdUtil;
import cn.andylhl.crowd.utils.ResultEntity;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * @Title: CrowdExceptionResolver
 * @Description: 异常处理（用于该项目统一进行异常处理）
 * @author: lhl
 * @date: 2020/12/22 15:23
 */

@ControllerAdvice //表示当前类是一个基于注解的异常处理类
public class CrowdExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(CrowdExceptionResolver.class);

    /**
     * 出现访问受保护资源异常，则跳转到登录页面
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(AccessForbiddenException.class)
    public ModelAndView resolveAccessForbiddenException(
            AccessForbiddenException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        logger.info("执行异常处理：AccessForbiddenException");
        String viewname = "admin-login";
        return commonReslove(viewname, exception, request, response);
    }


    /**
     * 针对出现登录失败的异常处理方法，出现登录失败异常，返回登录页面
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        logger.info("执行异常处理-登录失败异常：LoginFailedException");
        String viewname = "admin-login";
        return commonReslove(viewname, exception, request, response);
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView resolveNullPointerException(
            NullPointerException exception,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        logger.info("执行异常处理：NullPointerException");
        String viewname = "system-error";
        return commonReslove(viewname, exception, request, response);
    }

    public ModelAndView commonReslove(
            //要跳转到的视图
            String viewname,
            //实际捕获到的异常类型
            Exception exception,
            //当前请求对象
            HttpServletRequest request,
            //当前响应对象
            HttpServletResponse response ) throws IOException {
        //1. 判断当前请求类型
        Boolean judgeResult = CrowdUtil.judgeRequestType(request);

        //2. 如果是ajax请求
        if (judgeResult) {
            //3. 创建返回结果对象, 将异常信息封装
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //4. 创建Gson对象
            Gson gson = new Gson();
            //5. 将结果对象转换为json字符串
            String json = gson.toJson(resultEntity);
            //6. 将该json串作为响应结果进行返回给浏览器端
            response.getWriter().println(json);
            // 7. 执行到该处已经响应了json数据，所以不再提供视图
            return null;
        }
        //8. 执行到这里，说明请求不是ajax请求，创建ModleAndView对象进行跳转到目标视图
        ModelAndView mv = new ModelAndView();
        //9. 将Exception对象封装到模型中
        mv.addObject(Constant.ATTR_NAME_EXCEPTION, exception);
        //10. 设置对象视图名称
        mv.setViewName(viewname);
        //11. 返回ModelAndView对象
        return mv;

    }
}
