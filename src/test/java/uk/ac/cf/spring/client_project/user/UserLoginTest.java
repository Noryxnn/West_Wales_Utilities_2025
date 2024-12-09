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

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserLoginService userLoginService;

    @BeforeEach
    public void setUp() {

    }


    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        mockMvc.perform(post("/login")
                        .param("email", "invalid@example.com")  // Invalid email for testing
                        .param("password", "wrongPassword"))  // Invalid password for testing
                .andExpect(status().isOk())  // Expect the same page to reload (200 OK)
                .andExpect(view().name("user/loginForm"))  // Expect to return to the login form
                .andExpect(model().attributeExists("error")); // Error message should be present
    }
}
