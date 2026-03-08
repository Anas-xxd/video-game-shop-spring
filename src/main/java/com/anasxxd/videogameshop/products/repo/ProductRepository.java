package com.anasxxd.videogameshop.products.repo;

import com.anasxxd.videogameshop.products.Product;
import com.anasxxd.videogameshop.products.ProductType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbc;

    public ProductRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (rs, rowNum) -> {

        long id = rs.getLong("product_id");
        ProductType type = ProductType.valueOf(rs.getString("type"));
        String name = rs.getString("product_name");
        String developer = rs.getString("developer");
        String company = rs.getString("company");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        String platform = rs.getString("platform");
        int stock = rs.getInt("stock");
        double price = rs.getDouble("price");

        return new Product(id, type, name, developer, company, releaseDate, platform, stock, price);
    };

    public List<Product> listAll() {
        String sql = "SELECT product_id, type, product_name, developer, company, release_date, platform, stock, price FROM products";
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
}