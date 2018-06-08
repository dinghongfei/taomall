package com.taomall.content.service.impl;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.JsonUtils;
import com.taomall.content.service.ContentService;
import com.taomall.dao.ContentMapper;
import com.taomall.jdeis.JedisClient;
import com.taomall.pojo.Content;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("contentService")
public class ContentServiceImpl implements ContentService{

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;


    @Override
    public TaomallResult addContent(Content content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

        //同步缓存
        //删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaomallResult.ok();
    }

    @Override
    public List<Content> getContentListByCid(Long cid) {

        //先查缓存，添加缓存不能影响正常业务逻辑
        try {
            String json = jedisClient.hget(INDEX_CONTENT, cid + "");
            if (StringUtils.isNotBlank(json)){
                //缓存不为空时返回
                return JsonUtils.jsonToList(json, Content.class);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        //缓存没有命中，查数据库
        List<Content> contentList = contentMapper.selectListByCategoryId(cid);
        //添加缓存
        try {
            jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(contentList));
        } catch (Exception e){
            e.printStackTrace();
        }
        //返回结果
        return contentList;

    }
}
