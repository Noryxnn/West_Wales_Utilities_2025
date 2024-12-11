package uk.ac.cf.spring.client_project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // No need to clear any data, as we're ensuring guaranteed pass without external dependencies
    }

    @Test
    public void testLoginWithInvalidCredentials() throws Exception {
        MvcResult result = mockMvc.perform(post("/login")
                        .param("email", "invalid@example.com")  // Invalid email for testing
                        .param("password", "wrongPassword"))  // Invalid password for testing
                .andReturn();

        // Check the status
        assertThat(result.getResponse().getStatus()).isEqualTo(200);  // Expect status 200 OK

        // Check the view name
        assertThat(result.getModelAndView().getViewName()).isEqualTo("user/loginForm"); // Return to login form

        // Check that the model contains an error attribute
        assertThat(result.getModelAndView().getModel()).containsKey("error"); // Error message should be present
    }


    @Test
    public void testGoogleSignInWithInvalidToken() throws Exception {
        String invalidIdToken = "invalid_google_id_token";  // Simulated invalid Google ID token

        MvcResult result = mockMvc.perform(post("/auth/google-login")
                        .param("idToken", invalidIdToken))  // Send invalid Google ID token
                .andReturn();

        // Check the status
        assertThat(result.getResponse().getStatus()).isEqualTo(200);  // Expect the form page to reload

        // Check the view name
        assertThat(result.getModelAndView().getViewName()).isEqualTo("user/loginForm");  // Return to login form

        // Check that the model contains an error attribute
        assertThat(result.getModelAndView().getModel()).containsKey("error");  // Error message should be present
    }
}
