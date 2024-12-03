package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerTests {
    @Autowired
    private MockMvc mvc;


    @Test
    public void shouldRenderRequestForm() throws Exception {
        mvc.perform(get("/requests/new"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("request/request-form"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    public void shouldRenderConfirmationPageWhenRequestInSession() throws Exception {
        // Create a mock request object
        Request mockRequest = new Request(
                1L,
                1L,
                LocalDateTime.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2)
        );

        // Simulate session with mock request
        mvc.perform(get("/requests/confirmation")
                        .sessionAttr("request", mockRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("request/confirmation"))
                .andExpect(model().attributeExists("request"))
                .andExpect(model().attribute("request", mockRequest));
    }
}
