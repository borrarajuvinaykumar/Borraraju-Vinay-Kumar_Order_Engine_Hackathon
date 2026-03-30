package com.ecommerce.order_engine.controller;

import com.ecommerce.order_engine.model.Cart;
import com.ecommerce.order_engine.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long userId,
                           @RequestParam Long productId,
                           @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    @DeleteMapping("/remove")
    public String removeFromCart(@RequestParam Long userId,
                                @RequestParam Long productId) {
        return cartService.removeFromCart(userId, productId);
    }

    @GetMapping("/{userId}")
    public Cart viewCart(@PathVariable Long userId) {
        return cartService.viewCart(userId);
    }
}