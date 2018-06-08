package com.taomall.service;

import com.taomall.common.pojo.EasyUIDataGridResult;
import com.taomall.common.pojo.TaomallResult;
import com.taomall.pojo.Item;

/**
 * @author dhf
 */
public interface ItemService {

    /**
     * 根据产品id获取产品
     * @param itemId
     * @return
     */
    Item getItemById(Long itemId);

    /**
     * 获取产品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    EasyUIDataGridResult<Item> getItemList(int pageNum,int pageSize);

    TaomallResult addItem(Item item,String desc);



}
