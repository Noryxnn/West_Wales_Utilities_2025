package uk.ac.cf.spring.client_project.user;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {
        userRepository.addUser(user); // Saves a new user
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id); // Retrieves a user by ID
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers(); // Retrieves all users
    }
}
