package com.anasxxd.videogameshop.cart.repo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbc;

    public CartRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Long findOrCreateCartId(Long userId){
        String sql = """
                INSERT INTO carts(user_id)
                VALUES (?)
                ON DUPLICATE KEY UPDATE cart_id = LAST_INSERT_ID(cart_id)
                """;

        jdbc.update(sql, userId);
        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }
}