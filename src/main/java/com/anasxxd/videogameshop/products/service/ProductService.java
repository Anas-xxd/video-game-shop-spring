package com.anasxxd.videogameshop.products.service;

import com.anasxxd.videogameshop.products.Product;
import com.anasxxd.videogameshop.products.repo.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll() {
        return productRepository.listAll();
    }

    public Product create(Product p) {
        validateProduct(p);

        long newId = productRepository.insert(p);
        p.setProductId(newId);
        return p;
    }

    private void validateProduct(Product p) {
        if (p.getType() == null) {
            throw new IllegalArgumentException("Product type is required");
        }

        if (p.getName() == null || p.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (p.getCompany() == null || p.getCompany().isBlank()){
            throw new IllegalArgumentException("Product company is required");
        }

        if (p.getReleaseDate() == null){
            throw new IllegalArgumentException("Product release date is required");
        }

        if (p.getPrice() == null || p.getPrice() < 0){
            throw new IllegalArgumentException("Product price is required >= 0");
        }

        switch (p.getType()) {

            case GAME -> {
                if (p.getDeveloper() == null || p.getDeveloper().isBlank()) {
                    throw new IllegalArgumentException("Game must have a developer");
                }
                if (p.getStock() != null) {
                    throw new IllegalArgumentException("Game must not have stock");
                }
            }

            case CONSOLE -> {
                if (p.getStock() == null || p.getStock() < 0) {
                    throw new IllegalArgumentException("Console must have stock >= 0");
                }
            }

            case ACCESSORY -> {
                if (p.getStock() == null || p.getStock() < 0) {
                    throw new IllegalArgumentException("Accessory must have stock >= 0");
                }
            }

            default -> throw new IllegalArgumentException("Unknown product type: " + p.getType());
        }
    }
}