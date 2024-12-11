package uk.ac.cf.spring.client_project.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegisterRepositoryImpl implements RegisterRepository {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Register> userRowMapper;

    public RegisterRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setUserRowMapper();
    }

    private void setUserRowMapper() {
        userRowMapper = (rs, i) -> new Register(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getString("email")
        );
    }

    @Override
    public List<Register> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public Register getUserById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", userRowMapper, id);
    }

    @Override
    public void save(Register user) {
        jdbcTemplate.update("INSERT INTO users (first_name, last_name, password, email) VALUES (?, ?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail());
    }

    @Override
    public void deleteUserById(Integer id) {
        jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
    }

    @Override
    public void addUser(Register user) {
        String sql = "INSERT INTO users(first_name, last_name, password, email) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail());
    }
}
