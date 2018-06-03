package com.tb.fund.mapper;

import com.tb.fund.entity.SuccessKilled;
import com.tb.fund.entity.SuccessKilledKey;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SuccessKilledMapper {
    int deleteByPrimaryKey(SuccessKilledKey key);

    int insert(SuccessKilled record);

    int insertSelective(SuccessKilled record);

    SuccessKilled selectByPrimaryKey(SuccessKilledKey key);

    int updateByPrimaryKeySelective(SuccessKilled record);

    int updateByPrimaryKey(SuccessKilled record);
}