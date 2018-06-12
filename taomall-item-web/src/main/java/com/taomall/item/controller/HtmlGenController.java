package com.taomall.item.controller;

import freemarker.template.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 网页静态化处理controller
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    public String getHtml() throws Exception{
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        configuration.getTemplate("hello.ftl");
        Map data = new HashMap();
        data.put("hello", "spring freemarker test");
        Writer out = new FileWriter(new File("E:\\IDEA\\taomall\\taomall-item-web\\src\\main\\webapp\\WEB-INF\\ftl\\hello.txt"));
        out.close();
        return "ok";
    }

}
