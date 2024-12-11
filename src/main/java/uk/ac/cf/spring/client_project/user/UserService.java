package uk.ac.cf.spring.client_project.user;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void registerUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();

    // New method for Google Sign-In
    User registerGoogleUser(String email, String firstName, String lastName);
    Optional<User> findUserByEmail(String email);
}