package com.tb.fund.controller;

import com.tb.fund.dto.Exposer;
import com.tb.fund.dto.SeckillExecution;
import com.tb.fund.dto.SeckillResult;
import com.tb.fund.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    private SeckillService service;

    public SeckillResult<Exposer> exposer(Long seckillId) {
        SeckillResult<Exposer> result;

        try {
            Exposer exposer = service.exposerSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            e.printStackTrace();
            result = new SeckillResult<Exposer>(false, e.getMessage());

        }

        return result;
    }

    public SeckillResult<SeckillExecution> execute(){
        return null;
    }

    public SeckillResult<Long> time(){
        return null;
    }
}
