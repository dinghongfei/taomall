package com.taomall.controller;

import com.taomall.common.pojo.EasyUIDataGridResult;
import com.taomall.common.pojo.TaomallResult;
import com.taomall.pojo.Item;
import com.taomall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理controller
 * @author dhf
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/{itemId}")
    @ResponseBody
    public Item getItemById(@PathVariable Long itemId){
        Item item = itemService.getItemById(itemId);
        return item;
    }

    @RequestMapping(value = "/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page,Integer rows){
        EasyUIDataGridResult<Item> result = itemService.getItemList(page, rows);

        return result;
    }


    @RequestMapping(value = "/item/save")
    @ResponseBody
    public TaomallResult saveItem(Item item,String desc){
        TaomallResult result = itemService.addItem(item, desc);

        return result;
    }


}
