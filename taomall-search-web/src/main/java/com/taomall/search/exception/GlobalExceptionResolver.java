package com.taomall.search.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        //控制台打印
        e.printStackTrace();
        //日志写入
        logger.debug(o.getClass().getName());
        logger.error("有错误",e);
        //发邮件
        //发短信
        //展示错误页面
        ModelAndView modelAndView = new ModelAndView();
        //错误信息model
        modelAndView.addObject("message", "有错误");
        //jsp地址view
        modelAndView.setViewName("error/exception");

        return modelAndView;
    }
}
