package uk.ac.cf.spring.client_project.user;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;
    private final RegisterService registerService;

    public LoginServiceImpl(LoginRepository loginRepository, RegisterService registerService) {
        this.loginRepository = loginRepository;
        this.registerService = registerService;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        return loginRepository.validateUser(email, password);
    }

    @Override
    public Optional<Login> findByEmail(String email) {
        return loginRepository.findByEmail(email);
    }

    @Override
    public Login authenticateGoogleUser(String email, String firstName, String lastName) {
        // Check if user exists, if not, register
        Optional<Register> existingUser = registerService.findUserByEmail(email);

        if (existingUser.isEmpty()) {
            // If user doesn't exist, register them
            Register newUser = registerService.registerGoogleUser(email, firstName, lastName);

            // Convert to Login object
            Login login = new Login();
            login.setEmail(newUser.getEmail());
            login.setPassword(newUser.getPassword());
            return login;
        }

        // Convert existing user to Login object
        Login login = new Login();
        login.setEmail(existingUser.get().getEmail());
        login.setPassword(existingUser.get().getPassword());
        return login;
    }
}