package com.tb.fund.service;

import com.tb.fund.entity.Money;
import com.tb.fund.entity.User;

import java.util.List;
import java.util.Map;

public interface BaseService {
    /**
     * 尝试登陆
     * @param user
     * @return 用户信息
     */
    User tryLog(User user);

    /**
     * 检查用户名惟一
     * @param name
     * @return true:存在 false:不存在
     */
    boolean checkName(String name);

    /**
     * 新增用户
     * @param user
     * @return true 成功 false 失败
     */
    boolean insertUser(User user);

    /**
     * 取基金列表
     * @return 列表
     */
    List<Money> getFundList();

    /**
     * 购买基金
     * @param userId 用户id
     * @param fundId 基金id
     * @param count 购买数
     * @return 结果
     */
    Map<String,String> buyFund(Integer userId, Integer fundId, Integer count);
}
