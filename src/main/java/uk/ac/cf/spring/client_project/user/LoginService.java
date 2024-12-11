package uk.ac.cf.spring.client_project.user;

import java.util.Optional;

public interface LoginService {
    boolean authenticateUser(String email, String password);
    Optional<Login> findByEmail(String email);
    Login authenticateGoogleUser(String email, String firstName, String lastName);
}