package com.ecommerce.order_engine.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Cart {

    private Long userId;   

  
    private Map<Long, CartItem> items = new HashMap<>();

    public Cart() {}

    public Cart(Long userId) {
        this.userId = userId;
    }
}