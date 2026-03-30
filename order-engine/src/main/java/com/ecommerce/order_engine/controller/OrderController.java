package com.ecommerce.order_engine.controller;

import com.ecommerce.order_engine.model.Order;
import com.ecommerce.order_engine.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

   
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    
    @PostMapping("/place")
    public String placeOrder(@RequestParam Long userId) {
        return orderService.placeOrder(userId);
    }

    
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }
}