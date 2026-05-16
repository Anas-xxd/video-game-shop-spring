package com.anasxxd.videogameshop.cart;

public class CartItem {
    private long productId;
    private int quantity;

    public CartItem(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void setProduct(long productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId(){
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString() {
        return String.format("ProductId: %d - quantity: %d ", getProductId(), getQuantity());
    }
}