package com.taomall.order.interceptor;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.CookieUtils;
import com.taomall.common.utils.JsonUtils;
import com.taomall.pojo.User;
import com.taomall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    
    @Value("${TAOMALL_TOKEN}")
    private String TAOMALL_TOKEN;
    @Value("${SSO_URL}")
    private String SSO_URL;
    @Autowired
    private UserService userService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //执行handler之前先执行此方法
        //返回true：放行，返回false：拦截
        //1.从cookie取token信息
        String token = CookieUtils.getCookieValue(request, TAOMALL_TOKEN);
        //2.取不到token，没有登录，跳转到sso的登录页面，需要把当前请求的url作为参数传递给sso，sso登录成功后跳转回当前请求的页面
        if (StringUtils.isBlank(token)) {
            //取当前请求的url
            String requestURL = request.getRequestURL().toString();
            response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            //拦截
            return false;
        }
        //3.取到token，调用sso的服务，判断用户是否登录
        TaomallResult userByToken = userService.getUserByToken(token);
        if (userByToken.getStatus() != 200) {
            //4.如果没有取到用户信息，跳转到sso的登录页面，需要把当前请求的url作为参数传递给sso，sso登录成功后跳转回当前请求的页面
            //取当前请求的url
            String requestURL = request.getRequestURL().toString();
            response.sendRedirect(SSO_URL + "/page/login?url=" + requestURL);
            //拦截
            return false;
        }
        //5.取到用户信息，放行
        //这里把用户信息放到request中
        request.setAttribute("user",userByToken.getData());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，modeAndView返回执行
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //modeAndView返回之后
    }
}
