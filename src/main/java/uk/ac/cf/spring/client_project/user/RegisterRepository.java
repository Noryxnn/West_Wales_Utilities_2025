package uk.ac.cf.spring.client_project.user;

import java.util.List;

public interface RegisterRepository {
    List<Register> getAllUsers();
    Register getUserById(Integer id);
    void save(Register user);
    void deleteUserById(Integer id);
    void addUser(Register user);
}