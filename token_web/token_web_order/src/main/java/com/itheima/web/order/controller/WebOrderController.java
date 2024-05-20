package com.itheima.web.order.controller;

import com.itheima.order.feign.OrderFeign;
import com.itheima.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("worder")
public class WebOrderController {

    @Autowired
    private OrderFeign orderFeign;

    @GetMapping("/genToken")
    public String genToken() {
        String token = orderFeign.genToken();
        return token;
    }

    /**
     * 新增订单
     */
    @PostMapping("/addOrder2")
    public String addOrder(@RequestBody Order order) {
        return orderFeign.addOrder2(order);
    }
}
