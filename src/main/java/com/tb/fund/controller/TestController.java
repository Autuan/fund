package com.tb.fund.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
    @GetMapping("/test")
    public ModelAndView toTest(ModelAndView mav){
        mav.addObject("sex", "男");
        mav.setViewName("test");
        return mav;
    }
}
