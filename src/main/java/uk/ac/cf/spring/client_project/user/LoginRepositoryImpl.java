package uk.ac.cf.spring.client_project.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginRepositoryImpl implements LoginRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Login> findByEmail(String email) {
        try {
            Login login = jdbcTemplate.queryForObject(
                    "SELECT email, password FROM users WHERE email = ?",
                    (rs, rowNum) -> {
                        Login l = new Login();
                        l.setEmail(rs.getString("email"));
                        l.setPassword(rs.getString("password"));
                        return l;
                    },
                    email
            );
            return Optional.ofNullable(login);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean validateUser(String email, String password) {
        try {
            int count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?",
                    Integer.class,
                    email,
                    password
            );
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }
}