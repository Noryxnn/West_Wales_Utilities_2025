package uk.ac.cf.spring.client_project.user;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    User getUserById(Integer id);

    void save(User user);

    void deleteUserById(Integer id);

    void addUser(User user);
}
