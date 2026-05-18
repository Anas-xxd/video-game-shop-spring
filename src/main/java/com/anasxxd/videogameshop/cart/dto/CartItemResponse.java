package com.anasxxd.videogameshop.cart.dto;

public class CartItemResponse {
    private long productId;
    private int quantity;

    public CartItemResponse() {}

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getProductId(){
        return productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return String.format("ProductId: %d - quantity: %d ", getProductId(), getQuantity());
    }
}
