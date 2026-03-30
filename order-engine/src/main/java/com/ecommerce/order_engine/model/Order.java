package com.ecommerce.order_engine.model;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long id;
    private Long userId;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;
}