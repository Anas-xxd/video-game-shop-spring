package com.anasxxd.videogameshop.products.service;

import com.anasxxd.videogameshop.products.Product;
import com.anasxxd.videogameshop.products.ProductType;
import com.anasxxd.videogameshop.products.dto.AddProductRequest;
import com.anasxxd.videogameshop.products.dto.ProductResponse;
import com.anasxxd.videogameshop.products.dto.UpdateProductRequest;
import com.anasxxd.videogameshop.products.repo.ProductRepository;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse addProduct(AddProductRequest request) {
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

        Long newProductId = productRepository.addProduct(product);
        product.setProductId(newProductId);

        return getProductResponse(product);
    }

    public List<ProductResponse> listProducts() {
        List<ProductResponse> products = new ArrayList<>();
        for (Product product : productRepository.listProducts()){
            products.add(getProductResponse(product));
        }
        return products;
    }

    public ProductResponse getProduct(Long productId){
        return getProductResponse(findProductOrThrow(productId));
    }

    public ProductResponse updateProduct(Long productId, UpdateProductRequest request){
        Product product = findProductOrThrow(productId);

        if (request.getName() != null){
            product.setName(request.getName());
        }

        if (request.getDeveloper() != null){
            product.setDeveloper(request.getDeveloper());
        }

        if (request.getCompany() != null){
            product.setCompany(request.getCompany());
        }

        if (request.getReleaseDate() != null){
            product.setReleaseDate(request.getReleaseDate());
        }

        if (request.getPlatform() != null){
            product.setPlatform(request.getPlatform());
        }

        if (request.getStock() != null){
            product.setStock(request.getStock());
        }

        if (request.getPrice() != null){
            product.setPrice(request.getPrice());
        }

        validateProduct(product);
        productRepository.updateProduct(product);
        return getProductResponse(product);
    }

    public void deleteProduct(Long productId){
        findProductOrThrow(productId);
        productRepository.deleteProduct(productId);
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

    private Product findProductOrThrow(Long productId){
        Optional<Product> optionalProduct = productRepository.findProduct(productId);

        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        }

        throw new IllegalArgumentException("The product with the id: " + productId + " does not exist");
    }

    private void validateProduct(Product product) {
        if (product.getType() == null) {
            throw new IllegalArgumentException("Product type is required");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("Product name is required");
        }

        if (product.getCompany() == null || product.getCompany().isBlank()) {
            throw new IllegalArgumentException("Product company is required");
        }

        if (product.getReleaseDate() == null) {
            throw new IllegalArgumentException("Product release date is required");
        }

        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Product price is required >= 0");
        }

        switch (product.getType()) {

            case GAME -> {
                if (product.getDeveloper() == null || product.getDeveloper().isBlank()) {
                    throw new IllegalArgumentException("Game must have a developer");
                }
                if (product.getStock() != null) {
                    throw new IllegalArgumentException("Game must not have stock");
                }
            }

            case CONSOLE -> {
                if (product.getStock() == null || product.getStock() < 0) {
                    throw new IllegalArgumentException("Console must have stock >= 0");
                }
            }

            case ACCESSORY -> {
                if (product.getStock() == null || product.getStock() < 0) {
                    throw new IllegalArgumentException("Accessory must have stock >= 0");
                }
            }

            default -> throw new IllegalArgumentException("Unknown product type: " + product.getType());
        }
    }
}