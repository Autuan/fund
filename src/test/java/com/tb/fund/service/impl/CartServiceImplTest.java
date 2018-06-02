package com.tb.fund.service.impl;

import com.tb.fund.entity.Cart;
import com.tb.fund.entity.Money;
import com.tb.fund.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartServiceImplTest {
    @Autowired
    CartService cartService;
    @Test
    public void getCartListFromCookie() throws Exception {
    }

    @Test
    public void getCartListFromNoSql() throws Exception {
    }

    @Test
    public void getCartListFromSql() throws Exception {
        cartService.getCartListFromSql(1);

    }

    @Test
    public void updateItemNum() throws Exception {
    }

    @Test
    public void getCartById() throws Exception {
    }

    @Test
    public void presistentCart() throws Exception {
        Money m1 = new Money(1);
        Money m2 = new Money(2);
        Money m3 = new Money(3);
        Cart c1 = new Cart(m1, 4);
        Cart c2 = new Cart(m2, 5);
        Cart c3 = new Cart(m3, 6);
        List<Cart> cartList = new ArrayList<>(3);
        cartList.add(c1);
        cartList.add(c2);
        cartList.add(c3);
        cartService.presistentCart(cartList,3);

    }

    @Test
    public void noSqlPresistentCart() throws Exception{
        cartService.noSqlPresistentCart(3);
    }

}