package com.anasxxd.videogameshop.products;

import com.anasxxd.videogameshop.products.dto.AddProductRequest;
import com.anasxxd.videogameshop.products.dto.ProductResponse;
import com.anasxxd.videogameshop.products.dto.UpdateProductRequest;
import com.anasxxd.videogameshop.products.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ProductResponse addProduct(@Valid @RequestBody AddProductRequest request) {
        return productService.addProduct(request);
    }

    @GetMapping()
    public List<ProductResponse> listProducts() {
        return productService.listProducts();
    }

    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable Long productId){
        return productService.getProduct(productId);
    }

    @PatchMapping("/{productId}")
    public ProductResponse updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateProductRequest request
            ) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
    }
}