package com.anasxxd.videogameshop.products;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {
    private Long productId;
    private final ProductType type;
    private String name;
    private String developer;
    private String company;
    private LocalDate releaseDate;
    private String platform;
    private Integer stock;
    private BigDecimal price;

    public Product(Long productId, ProductType type, String name, String developer, String company, LocalDate releaseDate, String platform, Integer stock, BigDecimal price) {
        this.productId = productId;
        this.type = type;
        this.name = name;
        this.developer = developer;
        this.company = company;
        this.releaseDate = releaseDate;
        this.platform = platform;
        this.stock = stock;
        this.price = price;
    }

    public Long getProductId(){
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductType getType(){
        return type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDeveloper() {
        return developer;
    }
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative!");
        }
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        if (price.intValue() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public String toString() {
        return String.format("""
                        Type: %s
                        Name: %s
                        Developer: %s
                        Company: %s
                        Release Date: %s
                        Platforms: %s
                        Stock: %s
                        Price: $%.2f""",
                getType(), getName(), getDeveloper(), getCompany(), getReleaseDate(), getPlatform(), getStock(), getPrice());
    }
}