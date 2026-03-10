package com.anasxxd.videogameshop.products.service;

import com.anasxxd.videogameshop.products.Product;
import com.anasxxd.videogameshop.products.ProductType;
import com.anasxxd.videogameshop.products.dto.CreateProductRequest;
import com.anasxxd.videogameshop.products.dto.ProductResponse;
import com.anasxxd.videogameshop.products.repo.ProductRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        ProductType type = ProductType.valueOf(request.getType().toUpperCase());
        String name = request.getName();
        String dev = request.getDeveloper();
        String comp = request.getCompany();
        LocalDate date = request.getReleaseDate();
        String platform = request.getPlatform();
        Integer stock = request.getStock();
        BigDecimal price = request.getPrice();

        Product product = new Product(null, type, name, dev, comp, date, platform, stock, price);
        validateProduct(product);

        long newId = productRepository.insert(product);
        product.setProductId(newId);

        return getProductResponse(product);
    }

    private static @NonNull ProductResponse getProductResponse(Product product) {
        ProductResponse response = new ProductResponse();

        response.setId(product.getProductId());
        response.setType(product.getType().name());
        response.setName(product.getName());
        response.setDeveloper(product.getDeveloper());
        response.setCompany(product.getCompany());
        response.setReleaseDate(product.getReleaseDate());
        response.setPlatform(product.getPlatform());
        response.setStock(product.getStock());
        response.setPrice(product.getPrice());

        return response;
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

        if (p.getPrice() == null || p.getPrice().compareTo(BigDecimal.ZERO) < 0){
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