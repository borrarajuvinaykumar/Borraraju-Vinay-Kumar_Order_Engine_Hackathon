package com.ecommerce.order_engine.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FraudService {

    private final Map<String, Integer> userOrderCount = new HashMap<>();

    public boolean isFraud(String userId) {

        int count = userOrderCount.getOrDefault(userId, 0) + 1;
        userOrderCount.put(userId, count);

        return count > 3; 
    }
}