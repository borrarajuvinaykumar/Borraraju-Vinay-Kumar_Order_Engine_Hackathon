package com.ecommerce.order_engine.service;

import org.springframework.stereotype.Service;

@Service
public class CouponService {

    public int applyCoupon(String code, int total) {

        if (code == null) return total;

        switch (code) {
            case "SAVE10":
                return (int) (total * 0.9);
            case "FLAT200":
                return total - 200;
            default:
                return total;
        }
    }
}