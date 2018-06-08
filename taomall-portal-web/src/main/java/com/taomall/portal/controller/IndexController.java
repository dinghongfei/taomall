package com.taomall.portal.controller;

import com.taomall.common.utils.JsonUtils;
import com.taomall.content.service.ContentService;
import com.taomall.pojo.Content;
import com.taomall.portal.pojo.AD1Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页展示controller
 * @author dhf
 */
@Controller
public class IndexController {

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @Autowired
    private ContentService contentService;

    /**
     * 首页展示
     * 实现：web.xml里面配置了首页welcome-file，但是index.html文件不存在，然后被DispatcherServlet拦截到这里
     * @return
     */
    @RequestMapping("index")
    public String showIndex(Model model){
        //根据cid查询轮播图内容列表
        List<Content> contentList = contentService.getContentListByCid(AD1_CATEGORY_ID);
        //把列表转换为AD1Node列表
        List<AD1Node> ad1Nodes = new ArrayList<>();
        for (Content content : contentList) {
            AD1Node ad1Node = new AD1Node();
            ad1Node.setAlt(content.getTitle());
            ad1Node.setHeight(AD1_HEIGHT);
            ad1Node.setHeightB(AD1_HEIGHT_B);
            ad1Node.setWidth(AD1_WIDTH);
            ad1Node.setWidthB(AD1_WIDTH_B);
            ad1Node.setSrc(content.getPic());
            ad1Node.setSrc(content.getPic2());
            ad1Node.setHref(content.getUrl());
            ad1Nodes.add(ad1Node);
        }
        //列表转换为json
        String ad1 = JsonUtils.objectToJson(ad1Nodes);
        //json传递给页面
        model.addAttribute("ad1", ad1);

        return "index";
    }
}
