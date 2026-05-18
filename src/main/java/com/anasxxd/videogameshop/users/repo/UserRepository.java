package com.anasxxd.videogameshop.users.repo;

import com.anasxxd.videogameshop.users.UserRole;
import com.anasxxd.videogameshop.users.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        Long id = rs.getLong("user_id");
        UserRole userRole = UserRole.valueOf(rs.getString("user_role"));
        String key = rs.getString("login_key");
        String name = rs.getString("user_name");
        String email = rs.getString("email");
        String passHash = rs.getString("password_hash");

        return new User(id, userRole, key, name, passHash, email);
    };

    public List<User> listAllUsers() {
        String sql = """
                SELECT user_id, user_role, login_key, user_name, email, password_hash
                FROM users
                """;

        return jdbc.query(sql, USER_ROW_MAPPER);
    }

    public Optional<User> findUser(Long id){
        String sql = """
                SELECT user_id, user_role, login_key, user_name, email, password_hash
                FROM users 
                WHERE user_id = ?
                """;

        List<User> users = jdbc.query(sql, USER_ROW_MAPPER, id);

        if (users.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    public Long insert(User u) {
        String sql = """
                INSERT INTO users(user_role, login_key, user_name, email, password_hash)
                VALUES(?,?,?,?,?)
                """;

        jdbc.update(sql,
                u.getRole().name(),
                u.getLoginKey(),
                u.getName(),
                u.getEmail(),
                u.getPassword()
        );

        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public void update(User user){
        String sql = """
                UPDATE users
                SET login_key = ?, user_name = ?, email = ?, password_hash = ?
                WHERE user_id = ?
                """;

        jdbc.update(sql,
                user.getLoginKey(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getUserID()
        );
    }

    public void delete(Long id){
        String sql = """
                DELETE FROM users WHERE user_id = ?
                """;
        jdbc.update(sql, id);
    }
}
