package com.anasxxd.videogameshop.orders.repo;

import com.anasxxd.videogameshop.orders.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbc;

    public OrderRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Long addOrder(Order order){
        String sql = """
                INSERT INTO orders(user_id, total_price, status, created_at)
                VALUES(?,?,?,?)
                """;

        jdbc.update(sql,
                order.getUserId(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getCreatedAt()
        );

        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }
}