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

    public Long addUser(User user) {
        String sql = """
                INSERT INTO users(user_role, login_key, user_name, email, password_hash)
                VALUES(?,?,?,?,?)
                """;

        jdbc.update(sql,
                user.getRole().name(),
                user.getLoginKey(),
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );

        return jdbc.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    public List<User> listUsers() {
        String sql = """
                SELECT user_id, user_role, login_key, user_name, email, password_hash
                FROM users
                """;

        return jdbc.query(sql, USER_ROW_MAPPER);
    }

    public Optional<User> findUser(Long userId){
        String sql = """
                SELECT user_id, user_role, login_key, user_name, email, password_hash
                FROM users 
                WHERE user_id = ?
                """;

        List<User> users = jdbc.query(sql, USER_ROW_MAPPER, userId);

        if (users.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(users.get(0));
    }

    public void updateUser(User user){
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

    public void deleteUser(Long userId){
        String sql = """
                DELETE FROM users WHERE user_id = ?
                """;
        jdbc.update(sql, userId);
    }
}
