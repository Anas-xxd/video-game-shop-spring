package com.anasxxd.videogameshop.orders;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private Long orderId;
    private Long userId;
    private BigDecimal totalPrice;
    private OrderStatus orderStatus;
    Timestamp createdAt;

    public Order(Long orderId, Long userId, BigDecimal totalPrice,
                 OrderStatus orderStatus, Timestamp createdAt){
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return orderStatus;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
