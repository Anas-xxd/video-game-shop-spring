package com.anasxxd.videogameshop.cart.repo;

import com.anasxxd.videogameshop.cart.CartItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CartItemRepository {
    private final JdbcTemplate jdbc;

    public CartItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (rs, rowNum) -> {

        Long id = rs.getLong("product_id");
        return new CartItem(id, 1);
    };
}
