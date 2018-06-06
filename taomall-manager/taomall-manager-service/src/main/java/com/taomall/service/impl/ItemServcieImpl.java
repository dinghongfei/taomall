package com.taomall.service.impl;

import com.taomall.dao.ItemMapper;
import com.taomall.pojo.Item;
import com.taomall.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemService")
public class ItemServcieImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public Item getItemById(long itemId) {
        Item item = itemMapper.selectByPrimaryKey(itemId);
        return item;
    }
}
