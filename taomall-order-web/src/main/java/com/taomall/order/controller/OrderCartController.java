package com.taomall.order.controller;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.CookieUtils;
import com.taomall.common.utils.JsonUtils;
import com.taomall.order.pojo.OrderInfo;
import com.taomall.order.service.OrderService;
import com.taomall.pojo.Item;
import com.taomall.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderCartController {

    @Value("${TAOMALL_CART}")
    private String TAOMALL_CART;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request){
        //用户是登录状态
        //取用户信息，userId,这里的用户信息是在拦截器LoginInterceptor里面设置的
        User user = (User) request.getAttribute("user");
        //根据用户信息取收货地址列表，此项目没有对应的数据库表，用静态数据模拟
        //收货地址传递给前端页面
        //从cookie中取购物车商品列表传递给页面
        List<Item> cartItemList = getCartItemList(request);
        //返回视图
        request.setAttribute("cartList",cartItemList);
        return "order-cart";
    }

    private List<Item> getCartItemList(HttpServletRequest request){
        //cookie取购物车商品列表
        String json = CookieUtils.getCookieValue(request, TAOMALL_CART, true);
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        return JsonUtils.jsonToList(json, Item.class);
    }


    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model){
        TaomallResult result = orderService.createOrder(orderInfo);

        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());

        //送达时间
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
        return "success";
    }



}
