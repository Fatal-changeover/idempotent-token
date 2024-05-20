package com.itheima.order.feign;

import com.itheima.order.pojo.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "order")
@RequestMapping("/order")
public interface OrderFeign {
    @GetMapping("/genToken")
    public String genToken();

    @PostMapping("/genOrder")
    public String genOrder(@RequestBody Order order);

    @PostMapping("/addOrder2")
    public String addOrder2(@RequestBody Order  order);
}
