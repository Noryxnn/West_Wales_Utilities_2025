package uk.ac.cf.spring.client_project.user;

import java.util.List;

public interface UserService {
    void registerUser(User user);
    User getUserById(int id);
    List<User> getAllUsers();
}
