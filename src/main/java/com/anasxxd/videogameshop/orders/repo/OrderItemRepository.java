package com.anasxxd.videogameshop.orders.repo;

import com.anasxxd.videogameshop.orders.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepository {
    private final JdbcTemplate jdbc;

    public OrderItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Long addOrderItem(OrderItem orderItem){
        String sql = """
                INSERT INTO order_items(order_id, product_id, quantity, price_at_purchase)
                VALUES(?,?,?,?)
                """;

        jdbc.update(sql,
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getPriceAtPurchase()
        );

        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }
}