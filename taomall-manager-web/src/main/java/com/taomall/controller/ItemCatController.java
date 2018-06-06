package com.taomall.controller;

import com.taomall.common.pojo.EasyUITreeNode;
import com.taomall.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理controller
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> resultList = itemCatService.getItemCatList(parentId);
        return resultList;
    }
}
