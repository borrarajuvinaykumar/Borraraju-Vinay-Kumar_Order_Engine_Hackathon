package com.ecommerce.order_engine.controller;

import com.ecommerce.order_engine.model.Product;
import com.ecommerce.order_engine.service.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public String addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public String updateStock(@PathVariable Long id,
                             @RequestParam int stock) {
        return productService.updateStock(id, stock);
    }
}