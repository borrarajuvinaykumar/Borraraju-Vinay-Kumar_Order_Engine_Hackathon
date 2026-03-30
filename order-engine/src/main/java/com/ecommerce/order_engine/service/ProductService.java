package com.ecommerce.order_engine.service;

import com.ecommerce.order_engine.model.Product;
import com.ecommerce.order_engine.util.LockManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ProductService {

    private final Map<Long, Product> productMap = new HashMap<>();

    public String addProduct(Product product) {

        if (product.getId() == null) {
            return " Product ID cannot be null!";
        }

        if (productMap.containsKey(product.getId())) {
            return " Product already exists!";
        }

        if (product.getStock() < 0) {
            return " Invalid stock!";
        }

        productMap.put(product.getId(), product);
        return " Product added!";
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(productMap.values());
    }

    public Product getProduct(Long id) {
        return productMap.get(id);
    }

    public String updateStock(Long id, int stock) {

        Product product = productMap.get(id);

        if (product == null) return " Not found!";
        if (stock < 0) return " Invalid stock!";

        product.setStock(stock);
        return " Stock updated!";
    }

    public boolean reduceStock(Long productId, int qty) {

        ReentrantLock lock = LockManager.getLock(productId);

        lock.lock();
        try {
            Product p = productMap.get(productId);

            if (p == null || p.getStock() < qty) {
                return false;
            }

            p.setStock(p.getStock() - qty);
            return true;

        } finally {
            lock.unlock();
        }
    }

    public void increaseStock(Long productId, int qty) {

        ReentrantLock lock = LockManager.getLock(productId);

        lock.lock();
        try {
            Product p = productMap.get(productId);

            if (p != null) {
                p.setStock(p.getStock() + qty);
            }

        } finally {
            lock.unlock();
        }
    }
}