package com.ecommerce.order_engine.service;

import com.ecommerce.order_engine.model.Cart;
import com.ecommerce.order_engine.model.CartItem;
import com.ecommerce.order_engine.model.Product;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    private final Map<Long, Cart> userCarts = new HashMap<>();

    private final ProductService productService;

    public CartService(ProductService productService) {
        this.productService = productService;
    }

    private Cart getOrCreateCart(Long userId) {
        return userCarts.computeIfAbsent(userId, Cart::new);
    }

    public String addToCart(Long userId, Long productId, int quantity) {

        Product product = productService.getProduct(productId);

        if (product == null) {
            return " Product not found!";
        }

        if (quantity <= 0) {
            return " Quantity must be greater than 0!";
        }

        if (product.getStock() < quantity) {
            return " Not enough stock!";
        }

        Cart cart = getOrCreateCart(userId);

        CartItem existingItem = cart.getItems().get(productId);

        if (existingItem == null) {
            cart.getItems().put(productId,
                    new CartItem(productId, quantity));
        } else {
            existingItem.setQuantity(
                    existingItem.getQuantity() + quantity
            );
        }

        return " Added to cart!";
    }

    public String removeFromCart(Long userId, Long productId) {

        Cart cart = userCarts.get(userId);

        if (cart == null || !cart.getItems().containsKey(productId)) {
            return " Item not in cart!";
        }

        cart.getItems().remove(productId);

        return "Removed from cart!";
    }

   
    public Cart viewCart(Long userId) {
        return userCarts.getOrDefault(userId, new Cart(userId));
    }

    public void clearCart(Long userId) {
        userCarts.remove(userId);
    }
}