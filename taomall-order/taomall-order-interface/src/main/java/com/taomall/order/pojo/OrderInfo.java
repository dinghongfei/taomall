package com.taomall.order.pojo;

import com.taomall.pojo.Order;
import com.taomall.pojo.OrderItem;
import com.taomall.pojo.OrderShipping;

import java.util.List;

public class OrderInfo extends Order {

    private List<OrderItem> orderItems;
    private OrderShipping orderShipping;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(OrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
