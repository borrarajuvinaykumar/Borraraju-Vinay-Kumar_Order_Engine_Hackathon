package com.ecommerce.order_engine.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LockManager {

    private static final ConcurrentHashMap<Long, ReentrantLock> lockMap =
            new ConcurrentHashMap<>();

    public static ReentrantLock getLock(Long productId) {
        return lockMap.computeIfAbsent(productId,
                k -> new ReentrantLock());
    }
}