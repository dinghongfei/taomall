package com.taomall.sso.controller;


import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.CookieUtils;
import com.taomall.common.utils.JsonUtils;
import com.taomall.pojo.User;
import com.taomall.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Value("${TAOMALL_TOKEN}")
    private String TAOMALL_TOKEN;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaomallResult checkUserData(@PathVariable String param,@PathVariable Integer type){

        return userService.checkData(param, type);
    }

    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    @ResponseBody
    public TaomallResult register(User user){

        return userService.register(user);
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaomallResult login(String username, String password, HttpServletRequest request, HttpServletResponse response){
        TaomallResult result = userService.login(username, password);
        if (result.getStatus() == 200) {
            //登录成功，token写入cookie
            CookieUtils.setCookie(request,response,TAOMALL_TOKEN,result.getData().toString());
        }
        return result;
    }


    //produces = MediaType.APPLICATION_JSON_UTF8_VALUE为指定返回相应数据的content-type
    /*@RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getUserByToken(@PathVariable String token,String callback){
        TaomallResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            return callback + "(" + JsonUtils.objectToJson(result)  +")";
        }
        return JsonUtils.objectToJson(result);
    }*/


    //jsonp的第二种方法，仅限spring4.1以上版本
    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){
        TaomallResult result = userService.getUserByToken(token);
        //判断是否为jsonp请求
        if (StringUtils.isNotBlank(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }






}
