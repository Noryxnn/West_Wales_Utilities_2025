package uk.ac.cf.spring.client_project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(UserController.class)  // Test only the UserController layer
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        // Setup logic if needed before each test
    }

    @Test
    public void testLoginPageReturnsStatusOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/loginForm")); // Ensures the correct view is rendered
    }

    @Test
    public void testLoginSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "user")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/loginSuccess"))
                .andExpect(model().attribute("username", "user"));  // Verifies the correct username is passed to the model
    }

    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "wrongUser")
                        .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/loginForm"))
                .andExpect(model().attribute("error", "Invalid username or password"));
    }
}