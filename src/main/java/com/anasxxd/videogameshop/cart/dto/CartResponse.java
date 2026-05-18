package com.anasxxd.videogameshop.cart.dto;

public class CartResponse {
    private Long userId;
    private int numberOfItems;

    public CartResponse(){}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public String toString() {
        return super.toString();
    }
}
