package com.taomall.order.service.impl;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.dao.OrderItemMapper;
import com.taomall.dao.OrderMapper;
import com.taomall.dao.OrderShippingMapper;
import com.taomall.order.jedis.JedisClient;
import com.taomall.order.pojo.OrderInfo;
import com.taomall.order.service.OrderService;
import com.taomall.pojo.OrderItem;
import com.taomall.pojo.OrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${ORDER_ID_KEY}")
    private String ORDER_ID_KEY;
    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;
    @Value("${ORDER_ITEM_KEY}")
    private String ORDER_ITEM_KEY;


    @Override
    public TaomallResult createOrder(OrderInfo orderInfo) {
        //生成订单号，使用redis的incr
        if (!jedisClient.exists(ORDER_ID_KEY)) {
            jedisClient.set(ORDER_ID_KEY,ORDER_ID_BEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_KEY).toString();
        //向订单表中插入数据，需要补全pojo属性
        orderInfo.setOrderId(orderId);
        //免邮费
        orderInfo.setPostFee("0");
        //订单状态，未付款
        orderInfo.setStatus(1);
        //订单创建时间
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //订单表插入数据
        orderMapper.insert(orderInfo);


        //向订单明细表中插入数据
        List<OrderItem> orderItems = orderInfo.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            String orderItemId = jedisClient.incr(ORDER_ITEM_KEY).toString();
            orderItem.setItemId(orderItemId);
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }

        //向订单物流表中插入数据
        OrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);

        //返回订单号
        return TaomallResult.ok(orderId);
    }
}
