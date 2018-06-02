package com.tb.fund.service;

import com.tb.fund.entity.Cart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CartService {
    /**
     * 从Cookie中取购物车列表
     * @param request HttpServletRequest
     * @param s Cookie 名
     * @return 列表
     */
    List<Cart> getCartListFromCookie(HttpServletRequest request, String s);

    /**
     * 从NoSql数据库(Redis)中取购物车列表
     * @param userId 用户Id
     * @return 列表
     */
    List<Cart> getCartListFromNoSql(Integer userId);

    /**
     * 从关系型数据库(MySql)中取购物车
     * @param userId 用户ID
     * @return 列表
     */
    List<Cart> getCartListFromSql(Integer userId);

    /**
     * 更新购物车
     * @param list 购物车列表
     * @param itemId 商品信息
     * @param nums 商品数量
     * @return 结果
     */
    boolean updateItemNum(List<Cart> list,Integer itemId,Integer nums);

    /**
     * 通过商品Id获得一个购物车商品实例
     * @param itemId 商品Id
     * @return 购物车实例
     */
    Cart getCartById(Integer itemId);

    /**
     * 将购物车信息持久化到 MySql 中
     * @param list 购物车列表
     * @param userId 用户Id
     */
    void presistentCart(List<Cart> list,Integer userId);

    /**
     * 根据用户id 将 用户购物车转存到 NoSql中
     * @param userId
     */
    void noSqlPresistentCart(Integer userId);

    void emptyCartByUserId(Integer userId);
}
