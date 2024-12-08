package uk.ac.cf.spring.client_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserLoginService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    public boolean authenticateUser(String email, String password) {
        UserLogin user = userLoginRepository.findByEmail(email);
        if (user == null) {
            return false; // User does not exist
        }
        return password.equals(user.getPassword()); // Validate password directly (no encoding)
    }
}
