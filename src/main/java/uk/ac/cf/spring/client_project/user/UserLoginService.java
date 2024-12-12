package uk.ac.cf.spring.client_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;

    @Autowired
    public UserLoginService(UserLoginRepository userLoginRepository) {
        this.userLoginRepository = userLoginRepository;
    }

    @Autowired
    public PasswordEncoder passwordEncoder;

    public boolean authenticateUser(String email, String password) {
        UserLogin user = userLoginRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return passwordEncoder.matches(password, user.getPassword());
    }
}