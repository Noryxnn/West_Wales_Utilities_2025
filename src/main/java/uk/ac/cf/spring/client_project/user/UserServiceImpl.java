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
        // Check if user already exists
        Optional<User> existingUser = findUserByEmail(email);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // Create new user
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName(firstName != null ? firstName : "");
        newUser.setLastName(lastName != null ? lastName : "");

        // Set a placeholder password or generate a random one
        newUser.setPassword("GOOGLE_SSO_" + System.currentTimeMillis());

        userRepository.addUser(newUser);
        return newUser;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}