package com.tb.fund.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tb.fund.entity.Cart;
import com.tb.fund.entity.Money;
import com.tb.fund.mapper.CartMapper;
import com.tb.fund.mapper.MoneyMapper;
import com.tb.fund.service.CartService;
import com.tb.fund.util.CookieUtils;
import com.tb.fund.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.noggit.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    MoneyMapper moneyMapper;
    @Autowired
    private StringRedisTemplate redis;
    @Autowired
    private CartMapper cartMapper;

    // ------------------------- 业务处理 -------------------------
    @Override
    public List<Cart> getCartListFromCookie(HttpServletRequest request, String s) {
        String cookie = CookieUtils.getCookieValue(request,s,true);
        if ( StringUtils.isBlank(cookie) || cookie == null || cookie.length() <= 0) {
            return new ArrayList<>();
        }
        List<Cart> carts = JsonUtils.jsonToList(cookie,Cart.class);
        return carts;
    }

    @Override
    public List<Cart> getCartListFromNoSql(Integer userId) {
        String listString = redis.opsForValue().get("CART_USER_" + userId);
//        List<Cart> carts = JsonUtils.jsonToList(listString,Cart.class);
        List<Cart> carts1 = JSONObject.parseArray(listString, Cart.class);
        return carts1;
    }

    @Override
    public List<Cart> getCartListFromSql(Integer userId) {
        List<Map<String,Object>> mapList = cartMapper.getItemsByUserId(userId);
        List<Cart> carts = new ArrayList<>(16);
        mapList.forEach(item -> {
            Money money = new Money((Integer) item.get("itemId"));
            Cart cart = new Cart(money, (Integer) item.get("cartNumber"));
            carts.add(cart);
        });
        return carts;
    }

    @Override
    public boolean updateItemNum(List<Cart> list,Integer itemId,Integer nums) {
        boolean result = false;
        try {

            for (Cart item : list) {// 如果购物车已经存在该商品,修改数量
                if (item.getMoney().getMid() == itemId) {
                    item.setNumber(item.getNumber() + nums);
                    result = true;
                    break;
                }
            }

            if ( !result ) {
                Cart cart = getCartById(itemId);
                // 设置购物车信息
                cart.setNumber(nums);
                list.add(cart);
            }
        } catch (NullPointerException e) {
            System.out.println("------------------------- start -------------------------");
            e.printStackTrace();
            System.out.println("------------------------- over -------------------------");
            return false;
        }
        return result;
    }

    @Override
    public Cart getCartById(Integer itemId) {
        Money money = moneyMapper.selectByPrimaryKey(itemId);
        Cart cart = new Cart(money,0);

        return cart;
    }

    @Override
    public void presistentCart(List<Cart> list, Integer userId) {
        cartMapper.insertCart(list, userId);
    }

    @Override
    public void noSqlPresistentCart(Integer userId) {
        List<Cart> list = getCartListFromSql(userId);
        redis.opsForValue().set("CART_USER_"+userId,JSONObject.toJSONString(list), Long.parseLong("1000"));
    }

    @Override
    public void emptyCartByUserId(Integer userId) {
        cartMapper.emptyCartByUserId(userId);
    }
}
