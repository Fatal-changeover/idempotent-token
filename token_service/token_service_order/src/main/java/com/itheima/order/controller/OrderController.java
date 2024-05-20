package com.itheima.order.controller;

import com.itheima.annotation.Idemptent;
import com.itheima.order.pojo.Order;
import com.itheima.order.service.OrderService;
import com.itheima.util.IdWorker;
import com.netflix.ribbon.proxy.annotation.Http;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/genToken")
    public String genToken() {
        String token = String.valueOf(idWorker.nextId());
        redisTemplate.opsForValue()
                .set(token, 0, 30, TimeUnit.MINUTES);
        return token;
    }

//    @PostMapping("/addOrder")
//    public String addOrder(@RequestBody Order order, HttpServletRequest request) {
//        //获取
//        String token = request.getHeader("token");
//        //删除令牌
//        try {
//            if (redisTemplate.delete(token)) {
//                order.setId(String.valueOf(idWorker.nextId()));
//                order.setCreateTime(new Date());
//                order.setUpdateTime(new Date());
//                int result = orderService.addOrder(order);
//                if (result == 1) {
//                    System.out.println("success");
//                    return "success";
//                } else {
//                    System.out.println("fail");
//                    return "fail";
//                }
//            } else {
//                System.out.println("repeat request");
//                return "repeat request";
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("系统异常");
//        }
//
//    }

   // @Idemptent
    @PostMapping("/addOrder2")
    public String addOrder2(@RequestBody Order order) {
        order.setId(String.valueOf(idWorker.nextId()));
        order.setDeptName(order.getDeptName());
        int result = orderService.addOrder(order);
        if (result == 1) {
            System.out.println("success");
            return "success";
        } else {
            System.out.println("fail");
            return "fail";
        }
    }
}
