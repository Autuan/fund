package com.tb.fund.mapper;

import com.tb.fund.entity.Money;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MoneyMapper {
    int deleteByPrimaryKey(Integer mid);

    int insert(Money record);

    int insertSelective(Money record);

    Money selectByPrimaryKey(Integer mid);

    int updateByPrimaryKeySelective(Money record);

    int updateByPrimaryKey(Money record);

    List<Money> getMoneyList();
}