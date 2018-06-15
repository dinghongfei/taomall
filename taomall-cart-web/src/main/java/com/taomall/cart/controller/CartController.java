package com.taomall.cart.controller;

import com.taomall.common.pojo.TaomallResult;
import com.taomall.common.utils.CookieUtils;
import com.taomall.common.utils.JsonUtils;
import com.taomall.pojo.Item;
import com.taomall.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @Value("${TAOMALL_CART}")
    private String TAOMALL_CART;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;
    @Autowired
    private ItemService itemService;

    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response){
        //取购车商品列表
        List<Item> cartItemList = getCartItemList(request);
        //判断商品在购物车中是否存在
        boolean flag = false;
        for (Item item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //存在，数量相加
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        //不存在，添加一个新商品
        if (!flag) {
            //1.调用服务取商品信息
            Item item = itemService.getItemById(itemId);
            //2.设置购买数量
            item.setNum(num);
            //3.取第一张图片
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //商品添加到购物车
            cartItemList.add(item);
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request,response,TAOMALL_CART,JsonUtils.objectToJson(cartItemList),CART_EXPIER,true);
        //返回添加成功页面
        return "cartSuccess";
    }

    private List<Item> getCartItemList(HttpServletRequest request){
        //cookie取购物车商品列表
        String json = CookieUtils.getCookieValue(request, TAOMALL_CART, true);
        if (StringUtils.isBlank(json)) {
            return new ArrayList<>();
        }
        return JsonUtils.jsonToList(json, Item.class);
    }

    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request) {
        //cookie get cartItemList
        List<Item> cartItemList = getCartItemList(request);
        //list to jsp
        request.setAttribute("cartList",cartItemList);
        //back view
        return "cart";

    }

    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaomallResult updateItemNum(@PathVariable Long itemId,@PathVariable Integer num,
                                       HttpServletRequest request,HttpServletResponse response){
        //cookie get cartItemList
        List<Item> cartItemList = getCartItemList(request);
        //query the item by id
        for (Item item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //存在，数量相加
                item.setNum(item.getNum() + num);
                break;
            }
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request,response,TAOMALL_CART,JsonUtils.objectToJson(cartItemList),CART_EXPIER,true);

        //back ok
        return TaomallResult.ok();
    }

    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) {
        //cookie中取购物车列表
        List<Item> cartItemList = getCartItemList(request);
        //找到对应的商品
        for (Item item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //删除
                cartItemList.remove(item);
                break;
            }
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request,response,TAOMALL_CART,JsonUtils.objectToJson(cartItemList),CART_EXPIER,true);
        //重定向到购物车列表页面
        return "redirect:/cart/cart.html";

    }


}
