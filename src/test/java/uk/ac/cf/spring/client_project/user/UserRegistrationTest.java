package uk.ac.cf.spring.client_project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegistrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        // Clear database to avoid conflicts between tests
        userRepository.getAllUsers().forEach(user -> userRepository.deleteUserById(user.getUserId()));
    }

    @Test
    public void testRegistrationWithMissingFirstName() throws Exception {
        mockMvc.perform(post("/register")
                        .param("firstName", "") // Missing first name
                        .param("lastName", "Brown")
                        .param("password", "securePass123")
                        .param("email", "alice@example.com"))
                .andExpect(status().isOk()) // Expect the form page to reload
                .andExpect(view().name("user/registrationForm")) // Return to registration form
                .andExpect(model().attributeHasFieldErrors("user", "firstName")); // Missing first name validation error
    }

    @Test
    public void testRegistrationWithInvalidEmail() throws Exception {
        mockMvc.perform(post("/register")
                        .param("firstName", "Alice")
                        .param("lastName", "Brown")
                        .param("password", "securePass123")
                        .param("email", "invalid-email")) // Invalid email format
                .andExpect(status().isOk()) // Expect the form page to reload
                .andExpect(view().name("user/registrationForm")) // Return to registration form
                .andExpect(model().attributeHasFieldErrors("user", "email")); // Invalid email validation error
    }

}