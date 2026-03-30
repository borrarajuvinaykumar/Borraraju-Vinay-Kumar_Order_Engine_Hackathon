package com.ecommerce.order_engine.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PaymentService {

    private final Random random = new Random();

    public boolean processPaymentWithRetry() {

        int attempts = 3;

        while (attempts-- > 0) {

            boolean success = random.nextInt(100) < 60;

            if (success) {
                System.out.println(" Payment success");
                return true;
            }

            System.out.println(" Payment failed, retrying...");
        }

        return false;
    }
}