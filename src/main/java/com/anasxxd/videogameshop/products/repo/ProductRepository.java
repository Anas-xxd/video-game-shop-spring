package com.anasxxd.videogameshop.products.repo;

import com.anasxxd.videogameshop.products.Product;
import com.anasxxd.videogameshop.products.ProductType;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbc;

    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> {

        long id = rs.getLong("product_id");
        ProductType type = ProductType.valueOf(rs.getString("type").toUpperCase());
        String name = rs.getString("product_name");
        String developer = rs.getString("developer");
        String company = rs.getString("company");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        String platform = rs.getString("platform");
        Integer stock = rs.getInt("stock");
        if (rs.wasNull()) {
            stock = null;
        }
        BigDecimal price = rs.getBigDecimal("price");

        return new Product(id, type, name, developer, company, releaseDate, platform, stock, price);
    };

    public List<Product> listAll() {
        String sql = """
                SELECT product_id, type, product_name, developer, company, release_date, platform, stock, price
                FROM products
                """;

        return jdbc.query(sql, PRODUCT_ROW_MAPPER);
    }

    public long insert(Product p) {
        String sql = """
                INSERT INTO products (type, product_name, developer, company, release_date, platform, stock, price)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbc.update(sql,
                p.getType().name(),
                p.getName(),
                p.getDeveloper(),
                p.getCompany(),
                p.getReleaseDate(),
                p.getPlatform(),
                p.getStock(),
                p.getPrice()
        );

        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public void update(Product p){
        String sql = """
                UPDATE products
                SET product_name = ?, developer = ?, company = ?,
                release_date = ?, platform = ?, price = ?, stock = ?
                WHERE product_id = ?
                """;

        jdbc.update(sql,
                p.getName(),
                p.getDeveloper(),
                p.getCompany(),
                p.getReleaseDate(),
                p.getPlatform(),
                p.getPrice(),
                p.getStock(),
                p.getProductId()
        );
    }

    public void delete(Long id){
        String sql = """
                DELETE FROM products WHERE product_id = ?
                """;
        jdbc.update(sql, id);
    }

    public Optional<Product> findById(Long id){
        String sql = """
                SELECT product_id, type, product_name, developer, company, release_date, platform, stock, price
                FROM products WHERE product_id = ?
                """;

        List<Product> products = jdbc.query(sql, PRODUCT_ROW_MAPPER, id);

        if(products.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(products.get(0));
    }
}