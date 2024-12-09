package uk.ac.cf.spring.client_project.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<User> userRowMapper;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setUserRowMapper();
    }

    private void setUserRowMapper() {
        userRowMapper = (rs, i) -> new User(
                rs.getInt("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getString("company_name")
        );
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper);
    }

    @Override
    public User getUserById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", userRowMapper, id);
    }

    @Override
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO users (first_name, last_name, password, email, company_name) VALUES (?, ?, ?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getCompanyName());
    }

    @Override
    public void deleteUserById(Integer id) {
        jdbcTemplate.update("DELETE FROM users WHERE user_id = ?", id);
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO users(first_name, last_name, password, email, company_name) VALUES (?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getCompanyName());
    }
}
