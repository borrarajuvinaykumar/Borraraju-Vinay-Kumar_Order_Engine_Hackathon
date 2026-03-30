package com.ecommerce.order_engine.service;

import com.ecommerce.order_engine.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final Map<Long, Order> orderMap = new HashMap<>();

    private final CartService cartService;
    private final ProductService productService;
    private final PaymentService paymentService;

    public OrderService(CartService cartService,
                        ProductService productService,
                        PaymentService paymentService) {
        this.cartService = cartService;
        this.productService = productService;
        this.paymentService = paymentService;
    }

    public String placeOrder(Long userId) {

        Cart cart = cartService.viewCart(userId);

        if (cart.getItems().isEmpty()) {
            return " Cart is empty!";
        }

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem item : cart.getItems().values()) {

            boolean success = productService.reduceStock(
                    item.getProductId(),
                    item.getQuantity()
            );

            if (!success) {
                rollback(orderItems);
                return " Stock unavailable!";
            }

            Product product = productService.getProduct(item.getProductId());

            double price = product.getPrice();
            total += price * item.getQuantity();

            orderItems.add(new OrderItem(
                    item.getProductId(),
                    item.getQuantity(),
                    price
            ));
        }

        Order order = new Order();
        order.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.CREATED);

        orderMap.put(order.getId(), order);

        updateOrderStatus(order, OrderStatus.PENDING_PAYMENT);

        if (new Random().nextInt(100) < 20) {
            rollback(orderItems);
            updateOrderStatus(order, OrderStatus.FAILED);
            return " System failure!";
        }

        boolean paymentSuccess = paymentService.processPaymentWithRetry();

        if (!paymentSuccess) {
            rollback(orderItems);
            updateOrderStatus(order, OrderStatus.FAILED);
            return " Payment failed!";
        }

        updateOrderStatus(order, OrderStatus.PAID);

        cartService.clearCart(userId);

        return " Order placed! ID: " + order.getId();
    }

    private void rollback(List<OrderItem> items) {
        for (OrderItem item : items) {
            productService.increaseStock(
                    item.getProductId(),
                    item.getQuantity()
            );
        }
    }

    public Order getOrder(Long orderId) {
        return orderMap.get(orderId);
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {

        switch (current) {

            case CREATED:
                return next == OrderStatus.PENDING_PAYMENT;

            case PENDING_PAYMENT:
                return next == OrderStatus.PAID ||
                       next == OrderStatus.FAILED;

            case PAID:
                return next == OrderStatus.SHIPPED;

            case SHIPPED:
                return next == OrderStatus.DELIVERED;

            default:
                return false;
        }
    }

    private void updateOrderStatus(Order order, OrderStatus newStatus) {

        if (!isValidTransition(order.getStatus(), newStatus)) {
            throw new RuntimeException(
                    "Invalid status transition: " +
                    order.getStatus() + " → " + newStatus
            );
        }

        order.setStatus(newStatus);
    }
}