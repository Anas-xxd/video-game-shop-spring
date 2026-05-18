package com.anasxxd.videogameshop.cart.repo;

import com.anasxxd.videogameshop.cart.CartItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CartItemRepository {
    private final JdbcTemplate jdbc;

    public CartItemRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (rs, rowNum) -> {
        Long productId = rs.getLong("product_id");
        int quantity = rs.getInt("quantity");

        return new CartItem(productId, quantity);
    };

    public void upsertItem(Long cartId, CartItem cartItem){
        String sql = """
                INSERT INTO cart_items(cart_id, product_id, quantity)
                VALUES (?,?,?)
                ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)
                """;

        jdbc.update(sql,
                cartId,
                cartItem.getProductId(),
                cartItem.getQuantity()
        );
    }

    public List<CartItem> getCart(Long cartId){
        String sql = """
                SELECT product_id, quantity
                FROM cart_items
                WHERE cart_id = ?
                """;

        return jdbc.query(sql, CART_ITEM_ROW_MAPPER, cartId);
    }

    public Optional<CartItem> getCartItemById(Long cartId, Long productId){
        String sql = """
                SELECT product_id, quantity
                FROM cart_items
                WHERE cart_id = ? AND product_id = ?
                """;
        List<CartItem> cartItems = jdbc.query(sql,
                CART_ITEM_ROW_MAPPER,
                cartId,
                productId);

        if(cartItems.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(cartItems.get(0));
    }

    public void updateCartItem(Long cartId, CartItem cartItem){
        String sql = """
                UPDATE cart_items
                SET quantity = ?
                WHERE cart_id = ? AND product_id = ?
                """;

        jdbc.update(sql,
                cartItem.getQuantity(),
                cartId,
                cartItem.getProductId());
    }

    public void deleteCartItem(Long cartId, Long productId){
        String sql = """
                DELETE FROM cart_items WHERE cart_id = ? AND product_id = ?
                """;

        jdbc.update(sql, cartId, productId);
    }

    public void deleteCart(Long cartId){
        String sql = """
                DELETE FROM carts WHERE cart_id = ?
                """;

        jdbc.update(sql, cartId);
    }
}