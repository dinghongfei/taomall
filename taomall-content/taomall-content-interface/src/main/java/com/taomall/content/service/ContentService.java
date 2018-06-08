package com.taomall.content.service;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.pojo.Content;

import java.util.List;

public interface ContentService {

    TaomallResult addContent(Content content);
    List<Content> getContentListByCid(Long cid);

}
