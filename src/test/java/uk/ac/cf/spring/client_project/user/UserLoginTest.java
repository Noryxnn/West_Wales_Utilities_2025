package uk.ac.cf.spring.client_project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                        .with(csrf())
                        .param("email", "invalid@example.com")  // Invalid email for testing
                        .param("password", "wrongPassword"))  // Invalid password for testing
                .andExpect(status().is3xxRedirection())  // Expect a redirect
                .andExpect(redirectedUrl("/login?error"));
    }
}
