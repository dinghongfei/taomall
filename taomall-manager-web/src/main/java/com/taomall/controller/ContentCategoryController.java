package com.taomall.controller;

import com.taomall.common.pojo.EasyUITreeNode;
import com.taomall.common.pojo.TaomallResult;
import com.taomall.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类controller
 * @author dhf
 */
@Controller
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId){

        return contentCategoryService.getContentCategoryList(parentId);
    }

    @RequestMapping("/content/category/create")
    @ResponseBody
    public TaomallResult addContentCategory(Long parentId,String name){

        return contentCategoryService.addContentCategory(parentId, name);
    }

    @RequestMapping("/content/category/update")
    @ResponseBody
    public TaomallResult updateContentCategory(Long id,String name){

        return contentCategoryService.updateContentCategory(id, name);
    }

}
