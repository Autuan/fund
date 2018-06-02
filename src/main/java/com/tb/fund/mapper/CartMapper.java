package com.tb.fund.mapper;

import com.tb.fund.entity.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface CartMapper {

    List<Map<String,Object>> getItemsByUserId(Integer userId);

    void insertCart(@Param("list") List<Cart> list,
                    @Param("userId") Integer userId);

    void emptyCartByUserId(Integer userId);
}
