package com.taomall.item.controller;

import com.taomall.item.pojo.ItemResult;
import com.taomall.pojo.Item;
import com.taomall.pojo.ItemDesc;
import com.taomall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showIndex(@PathVariable Long itemId, Model model){
        Item item = itemService.getItemById(itemId);
        ItemResult itemResult = new ItemResult(item);

        ItemDesc itemDesc = itemService.getItemDescById(itemId);

        model.addAttribute("item", itemResult);
        model.addAttribute("itemDesc", itemDesc);

        return "item";

    }

}
