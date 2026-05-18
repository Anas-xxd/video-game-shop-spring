package com.anasxxd.videogameshop.cart.dto;

import jakarta.validation.constraints.Positive;

public class UpdateCartItemRequest {
    @Positive
    Integer quantity;

    public UpdateCartItemRequest(){}

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String toString() {
        return super.toString();
    }
}
