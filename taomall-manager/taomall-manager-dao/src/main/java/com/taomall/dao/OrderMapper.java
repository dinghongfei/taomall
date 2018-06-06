package com.taomall.dao;

import com.taomall.pojo.Order;

public interface OrderMapper {
    int insert(Order record);

    int insertSelective(Order record);
}