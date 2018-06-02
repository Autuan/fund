package com.tb.fund.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartControllerTest {
    @Autowired
    private CartController cartController;

    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();
    @Test
    public void addItemToCart() throws Exception {

        Integer userId = 2;
        Integer itemId = 1;
        Integer nums = 5;
        String string = cartController.addItemToCart(userId,itemId,nums,request,response);
        System.out.println(string);
    }

    @Test
    public void getUserCartList() throws Exception {
    }

    @Test
    public void editNum() throws Exception {
        cartController.editNum(request,response,3,1,11);
    }

    @Test
    public void delteCartItem() throws Exception {
        String s = cartController.delteCartItem(2, 3, request, response);
        System.out.println(s);
    }

}