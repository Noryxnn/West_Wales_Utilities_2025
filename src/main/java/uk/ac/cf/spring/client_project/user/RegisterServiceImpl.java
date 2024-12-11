package uk.ac.cf.spring.client_project.user;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegisterServiceImpl implements RegisterService {
    private final RegisterRepository registerRepository;

    public RegisterServiceImpl(RegisterRepository registerRepository) {
        this.registerRepository = registerRepository;
    }

    @Override
    public void registerUser(Register user) {
        registerRepository.addUser(user);
    }

    @Override
    public Register getUserById(int id) {
        return registerRepository.getUserById(id);
    }

    @Override
    public List<Register> getAllUsers() {
        return registerRepository.getAllUsers();
    }

    @Override
    public Register registerGoogleUser(String email, String firstName, String lastName) {
        // Check if user already exists
        Optional<Register> existingUser = findUserByEmail(email);

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        // Create new user
        Register newUser = new Register();
        newUser.setEmail(email);
        newUser.setFirstName(firstName != null ? firstName : "");
        newUser.setLastName(lastName != null ? lastName : "");

        // Set a placeholder password or generate a random one
        newUser.setPassword("GOOGLE_SSO_" + System.currentTimeMillis());

        registerRepository.addUser(newUser);
        return newUser;
    }

    @Override
    public Optional<Register> findUserByEmail(String email) {
        return getAllUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}