package com.taomall.controller;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.search.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexManagerController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaomallResult importIndex(){
        return searchItemService.importItemsToIndex();
    }


}
