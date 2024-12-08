package uk.ac.cf.spring.client_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserLoginRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserLoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserLogin findByEmail(String email) {
        String sql = "SELECT user_id, email, password FROM users WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, rs -> {
            if (rs.next()) {
                return mapRowToUser(rs);
            }
            return null;
        });
    }

    private UserLogin mapRowToUser(ResultSet rs) throws SQLException {
        return new UserLogin(
                rs.getLong("user_id"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}

