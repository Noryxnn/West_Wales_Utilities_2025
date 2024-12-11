package uk.ac.cf.spring.client_project.user;

import java.util.Optional;

public interface LoginRepository {
    Optional<Login> findByEmail(String email);
    boolean validateUser(String email, String password);
}