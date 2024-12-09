package uk.ac.cf.spring.client_project.errors;

import jakarta.servlet.RequestDispatcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomErrorsTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void notFoundErrorReturns404Page() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 404))
                .andExpect(view().name("errors/404"));
    }

    @Test
    public void internalServerErrorReturns500Page() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 500))
                .andExpect(view().name("errors/500"));
    }

    @Test
    public void forbiddenErrorReturns403Page() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 403))
                .andExpect(view().name("errors/403"));
    }

    @Test
    public void badRequestErrorReturns400Page() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 400))
                .andExpect(view().name("errors/400"));
    }

    @Test
    public void unauthorizedErrorReturns401Page() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 401))
                .andExpect(view().name("errors/401"));
    }

    @Test
    public void defaultErrorReturnsGenericErrorPage() throws Exception {
        mockMvc.perform(get("/error")
                        .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, 123))
                .andExpect(view().name("errors/error"));
    }

}