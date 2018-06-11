package com.taomall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taomall.common.pojo.EasyUIDataGridResult;
import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.IDUtils;
import com.taomall.dao.ItemDescMapper;
import com.taomall.dao.ItemMapper;
import com.taomall.pojo.Item;
import com.taomall.pojo.ItemDesc;
import com.taomall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

@Service("itemService")
public class ItemServcieImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;

    //根据id获取
    @Resource(name = "itemAddtopic")
    private Destination destination;

    @Override
    public Item getItemById(Long itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Override
    public EasyUIDataGridResult<Item> getItemList(int pageNum,int pageSize) {
        //设置分页信息
        PageHelper.startPage(pageNum, pageSize);
        //执行查询
        List<Item> items = itemMapper.selectList();
        PageInfo<Item> pageInfo = new PageInfo<>(items);
        EasyUIDataGridResult<Item> result = new EasyUIDataGridResult<>();
        result.setRows(items);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public TaomallResult addItem(Item item, String desc) {
        //生成商品id
        final long itemId = IDUtils.genItemId();
        //补全item的属性
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        itemMapper.insert(item);
        //创建一个商品描述表对应的pojo
        ItemDesc itemDesc = new ItemDesc();
        //补全pojo的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDesc.setCreated(new Date());
        //向商品描述表插入数据
        itemDescMapper.insert(itemDesc);

        //向activeMQ发送商品添加消息
        jmsTemplate.send(destination, session -> {
            TextMessage textMessage = session.createTextMessage(itemId + "");
            return textMessage;
        });

        //返回结果
        return TaomallResult.ok();
    }
}
