package com.taomall.controller;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.content.service.ContentService;
import com.taomall.pojo.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public TaomallResult addContent(Content content){

        return contentService.addContent(content);
    }


}
