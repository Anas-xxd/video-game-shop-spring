package com.anasxxd.videogameshop.cart.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddCartItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;

    public AddCartItemRequest() {}

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId(){
        return productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String toString() {
        return String.format("ProductId: %d - quantity: %d ", getProductId(), getQuantity());
    }
}
