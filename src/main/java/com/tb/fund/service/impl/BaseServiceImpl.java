package com.tb.fund.service.impl;

import com.tb.fund.entity.Link;
import com.tb.fund.entity.Money;
import com.tb.fund.entity.User;
import com.tb.fund.mapper.LinkMapper;
import com.tb.fund.mapper.MoneyMapper;
import com.tb.fund.mapper.UserMapper;
import com.tb.fund.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseServiceImpl implements BaseService {
    // ------------------------- 自动注入 -------------------------
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    private LinkMapper linkMapper;

    // ------------------------- 业务处理 -------------------------
    @Override
    public User tryLog(User user) {
        user = userMapper.selectByNameAndPwd(user);
        if (user.getUid() > 0) {
            return user;
        }
        return null;
    }

    @Override
    public boolean checkName(String name) {
        Integer i = userMapper.checkName(name);
        return i > 0;
    }

    @Override
    public boolean insertUser(User user) {
        int i = userMapper.insertSelective(user);
        return i > 0;
    }

    @Override
    public List<Money> getFundList() {
        return moneyMapper.getMoneyList();
    }

    @Override
    public Map<String, String> buyFund(Integer userId, Integer fundId, Integer count) {
        Map<String , String> map = new HashMap<String,String>(1);
        Integer surplus = moneyMapper.selectByPrimaryKey(fundId).getSurplus();
        if (surplus < count) {
            map.put("result","false");
            map.put("describe","under money");
            return map;
        }
        // 修改记录
            Money money = new Money();
            money.setMid(fundId);
            money.setSum(surplus);
            moneyMapper.updateByPrimaryKeySelective(money);
        // 关连表增加
            Link link = new Link();
            link.setBuySum(count);
            link.setMid(fundId);
            link.setUid(userId);
            linkMapper.insertSelective(link);

        map.put("result","true");
        map.put("describe","success");
        return map;
    }
}
