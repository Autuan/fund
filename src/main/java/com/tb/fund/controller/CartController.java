package com.tb.fund.controller;

import com.alibaba.fastjson.JSONObject;
import com.tb.fund.config.SenderCompent;
import com.tb.fund.entity.Cart;
import com.tb.fund.entity.Money;
import com.tb.fund.listen.CartListen;
import com.tb.fund.service.CartService;
import com.tb.fund.util.CookieUtils;
import com.tb.fund.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private CartService cartService;
    @Autowired
    private SenderCompent sender;

    @RequestMapping("/addItemToCart")
    @ResponseBody
    public String addItemToCart(Integer userId, Integer itemId, Integer nums,
                                HttpServletRequest request, HttpServletResponse response){
        // 取购物车列表
        List<Cart> list = null;
        // 判断商品是否存在
        // cookie 验证存在
        list = cartService.getCartListFromCookie(request,"CART_COOKIE");

        if ( !list.isEmpty() && null != list ) {
            cartService.updateItemNum(list,itemId,nums);

            // 将最新数据更新
            // |_ Cookie
            CookieUtils.setCookie(request,response,"CART_COOKIE", JSONObject.toJSONString(list));
            // |_ NoSql
            redis.opsForValue().set("CART_USER_"+userId,JSONObject.toJSONString(list));
            // 持久化数据
            cartService.emptyCartByUserId(userId);
            cartService.presistentCart(list,userId);
            // 返回数据
            return "successFromCookie";
        }


        // NoSql 验证存在
        list = cartService.getCartListFromNoSql(userId);
        if ( null != list && !list.isEmpty()  ) {
            // 商品数量修改或新增
            cartService.updateItemNum(list,itemId,nums);
            // 将最新数据更新
            // |_Cookie
            CookieUtils.setCookie(request,response,"CART_COOKIE", JSONObject.toJSONString(list));
            // |_NoSql
            redis.opsForValue().set("CART_USER_"+userId,JSONObject.toJSONString(list));
            // 持久化数据
            cartService.emptyCartByUserId(userId);
            cartService.presistentCart(list,userId);
            return "successFromNoSql";
        }


        // Sql 验证存在
        list = cartService.getCartListFromSql(userId);

        if ( null != list && !list.isEmpty() ) {
            // 商品数量修改 或 增加
            cartService.updateItemNum(list,itemId,nums);
            // 将最新数据更新
            // |_Cookie
            CookieUtils.setCookie(request,response,"CART_COOKIE", JSONObject.toJSONString(list));
            // |_NoSql
            redis.opsForValue().set("CART_USER_"+userId,JSONObject.toJSONString(list));
            // 持久化数据
            cartService.emptyCartByUserId(userId);
            cartService.presistentCart(list,userId);
            return "successFromSql";
        }

        // 第一次添加购物车商品
        list = new ArrayList<Cart>(16);
        cartService.updateItemNum(list,itemId,nums);
        // 将最新数据更新
        // |_Cookie
        CookieUtils.setCookie(request,response,"CART_COOKIE", JSONObject.toJSONString(list));
        // |_NoSql
        redis.opsForValue().set("CART_USER_"+userId,JSONObject.toJSONString(list));
        // 持久化数据
        cartService.emptyCartByUserId(userId);
        cartService.presistentCart(list,userId);
        return "successFromFirst";

    }


    @RequestMapping("/getCartList")
    @ResponseBody
    public List<Cart> getCartList(Integer userId,HttpServletRequest request){
        List<Cart> list = cartService.getCartListFromCookie(request, "CART_COOKIE");
        if ( list.isEmpty() || null == list ) {
            list = cartService.getCartListFromNoSql(userId);
        }
        if (list.isEmpty() || null == list ) {
            list = cartService.getCartListFromSql(userId);
        }
        return list;
    }


    @RequestMapping("/editNum")
    @ResponseBody
    public String editNum(HttpServletRequest request,HttpServletResponse response,Integer userId,Integer mid,Integer num){
        // 从NoSql中获取最新购物车列表
        List<Cart> list = cartService.getCartListFromNoSql(userId);
        for (Cart item : list) {
            if (item.getMoney().getMid() == mid) {
                item.setNumber(num);
                break;
            }
        }

        CookieUtils.setCookie(request,response,"CART_COOKIE", JSONObject.toJSONString(list));
        // 更新缓存
        redis.opsForValue().set("CART_USER_"+userId,JSONObject.toJSONString(list));
        // 消息队列
        // 持久化数据
        cartService.emptyCartByUserId(userId);
        cartService.presistentCart(list,userId);
        return "success";
    }

    @RequestMapping("/deleteCartItem")
    @ResponseBody
    public String delteCartItem(Integer userId,Integer mid,HttpServletRequest request,HttpServletResponse response) {
        // 删除的话从NoSql中获取最新购物车列表
        List<Cart> list = cartService.getCartListFromNoSql(userId);
        if ( null != list && !list.isEmpty() ) {
            for(Cart cart : list) {
                if ( cart.getMoney().getMid() == mid ) {
                    list.remove(cart);
                    break;
                }
            }
        }
   //     CookieUtils.setCookie(request,response,"CART_COOKIE", JsonUtils.objectToJson(list));
        // 重置Redis
        redis.opsForValue().set("CART_USER_"+userId,JsonUtils.objectToJson(list));
        // 持久化数据
        cartService.emptyCartByUserId(userId);
        cartService.presistentCart(list,userId);
        return "success";
    }
}
