package com.taomall.dao;

import com.taomall.pojo.ItemDesc;

public interface ItemDescMapper {
    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);
}