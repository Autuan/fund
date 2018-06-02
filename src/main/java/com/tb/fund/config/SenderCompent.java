package com.tb.fund.config;

import com.tb.fund.entity.Cart;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SenderCompent {
    @Autowired
    private AmqpTemplate rabbit;

    public void sendCart(){
        String context = "";
        this.rabbit.convertAndSend("cart",context);
    }


    public void sendUpdateNoSqlCart(List<Cart> carts){
        String context = "";
        this.rabbit.convertAndSend("updateNoSqlCart",carts);
    }

    public void sendUpdateSqlCart(List<Cart> carts){
        String context = "";
        this.rabbit.convertAndSend("updateNoSqlCart",carts);
    }

    public void sendUpdateNoSqlCart2(List<Cart> carts){
        String context = "";
        this.rabbit.convertAndSend("updateNoSqlCart",carts);
    }
}
