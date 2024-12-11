package uk.ac.cf.spring.client_project.user;

import java.util.List;
import java.util.Optional;

public interface RegisterService {
    void registerUser(Register user);
    Register getUserById(int id);
    List<Register> getAllUsers();

    // New method for Google Sign-In
    Register registerGoogleUser(String email, String firstName, String lastName);
    Optional<Register> findUserByEmail(String email);
}