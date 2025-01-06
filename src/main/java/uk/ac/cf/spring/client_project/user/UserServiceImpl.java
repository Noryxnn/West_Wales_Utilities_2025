package uk.ac.cf.spring.client_project.user;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public User registerGoogleUser(String email, String firstName, String lastName) {
        return null;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public void assignRole(String email, String role) {
        String sql = "INSERT INTO user_roles (email, role_id) " +
                "VALUES (?, (SELECT role_id FROM roles WHERE role_name = ?))";
        userRepository.getJdbcTemplate().update(sql, email, role);
    }
}
