package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestControllerTests {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private RequestService requestService;

    @Test
    public void shouldRenderRequestForm() throws Exception {
        mvc.perform(get("/request"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("request/requestForm"))
                .andExpect(model().attributeExists("request"));
    }

    @Test
    public void shouldSaveRequestOnFormSubmission() throws Exception {
        mvc.perform(post("/request")
                        .param("userId", "1")
                        .param("locationId", "2")
                        .param("visitDate", "2024-12-01")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/request"));
        verify(requestService, times(1)).save(any(Request.class));
    }
}
