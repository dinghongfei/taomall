package com.taomall.order.service;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.order.pojo.OrderInfo;

public interface OrderService {

    TaomallResult createOrder(OrderInfo orderInfo);
}
