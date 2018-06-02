package com.tb.fund.controller;

import com.tb.fund.entity.Money;
import com.tb.fund.entity.User;
import com.tb.fund.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/base")
public class BaseController {
    // ------------------------- 自动注入 -------------------------
    @Autowired
    private BaseService baseService;

    // ------------------------- 业务处理 -------------------------

    //  =========== 用户模块 ==============

    /**
     * 用户登陆
     * @param user 用户登陆信息
     * @return 结果
     */
    @RequestMapping("/log")
    @ResponseBody
    public Map<String,String> userLog(User user, HttpSession session){
        Map<String,String> map = new HashMap<String, String>(1);
        try{
            User logUser = baseService.tryLog(user);
            if ( user!=null ) {
                // 信息存入Session
                session.setAttribute(logUser.getName(),logUser);
                // 返回
                map.put("result","true");
                map.put("describe","success");
            } else {
                map.put("result","false");
                map.put("describe","username or password is mistake");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result","false");
            map.put("describe","error 500");
        }

        return map;
    }

    /**
     * 用户注册
     * 注册时要求账号唯一
     * @param user 注册信息
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map<String,String> userRegister(User user){
        Map<String,String> map = new HashMap<String,String>(1);
        try{
            // 账号唯一验证
            boolean hasThis = baseService.checkName(user.getName());
            if ( hasThis ) {
                map.put("result","false");
                map.put("describe","have this name");
                return map;
            }
            // 账号注册
            boolean b = baseService.insertUser(user);
            map.put("result","true");
            map.put("describe","success");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("result","false");
            map.put("describe","error 500");
        }
        return map;
    }

    //  =========== 基金模块 ==============

    /**
     * 服基金列表
     * @return
     */
    @RequestMapping("/getList")
    @ResponseBody
    public List<Money> getMoneyList(){
        return baseService.getFundList();
    }

    /**
     * 购买基金
     * @param userId 用户id
     * @param fundId 基金id
     * @param count 购买量
     * @return
     */
    @RequestMapping("/buyFund")
    @ResponseBody
    public Map<String,String> buyFund(Integer userId,Integer fundId,Integer count){
        Map<String,String> map = new HashMap<>(1);
        try{
           map = baseService.buyFund(userId,fundId,count);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result","false");
            map.put("describe","error 500");
        }
        return map;
    }

    // ------------------------- 页面跳转 -------------------------
    /**
     * 跳转到列表页
     */
    @RequestMapping("/toList")
    public ModelAndView toList(String username, ModelAndView mav,HttpSession session){
        User user = (User)session.getAttribute(username);
        mav.addObject("username",user.getName());
        mav.addObject("userid",user.getUid());
        mav.setViewName("list");
        return mav;
    }

    /**
     * 跳转到登陆页
     */
    @RequestMapping("/toLog")
    public String log(){
        return "log";
    }

    @RequestMapping("/toCartList")
    public ModelAndView toCartList(String username, ModelAndView mav,HttpSession session){
        User user = (User)session.getAttribute(username);
        mav.addObject("username",user.getName());
        mav.addObject("userid",user.getUid());
        mav.setViewName("cartList");
        return mav;
    }
}
