package com.anasxxd.videogameshop.products;

import com.anasxxd.videogameshop.products.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tools.jackson.databind.cfg.HandlerInstantiator;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> listProducts() {
        return productService.listAll();
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        return productService.create(product);
    }
}