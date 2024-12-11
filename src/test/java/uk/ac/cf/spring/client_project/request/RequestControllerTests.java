package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
                LocalDate.now().plusDays(2),
                RequestStatus.PENDING
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

    @Test
    public void shouldReturnToFormOnValidationErrors() throws Exception {
        mvc.perform(post("/requests/new")
                        .param("userId", "invalid")
                        .param("requestDate", LocalDateTime.now().toString())
                        .param("visitStartDate", LocalDate.now().plusDays(1).toString())
                        .param("visitEndDate", LocalDate.now().plusDays(2).toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("request/request-form"))
                .andExpect(model().attributeHasFieldErrors("request", "userId"));
    }

    @Test
    public void shouldSubmitRequest() throws Exception {
        Request request = new Request(
                1L,
                1L,
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDate.of(2024, 12, 4),
                LocalDate.of(2024, 12, 5),
                RequestStatus.PENDING
        );

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("request", request);

        MvcResult result = mvc
                .perform(get("/requests/confirmation").session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("request/confirmation"))
                .andExpect(model().attributeExists("request"))
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        Request requestFromModel = (Request) modelAndView.getModel().get("request");
        assertNotNull(requestFromModel);
        assertEquals(request.getRequestId(), requestFromModel.getRequestId());
        assertEquals(request.getUserId(), requestFromModel.getUserId());
        assertEquals(request.getRequestDate(), requestFromModel.getRequestDate());
        assertEquals(request.getVisitStartDate(), requestFromModel.getVisitStartDate());
        assertEquals(request.getVisitEndDate(), requestFromModel.getVisitEndDate());
    }
}