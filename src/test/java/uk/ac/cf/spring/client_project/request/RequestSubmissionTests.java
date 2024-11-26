package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RequestSubmissionTests {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private RequestRepository requestRepository;

    @Test
    public void shouldInsertRequestIntoDatabase() throws Exception {
        // Given: Prepare data to be submitted to the form.
        long userId = 1L;
        long locationId = 1L;
        LocalDate visitDate = LocalDate.of(2024, 12, 1);

        // When: User submits the request form.
        mvc.perform(post("/request")
                        .param("userId", String.valueOf(userId))
                        .param("locationId", String.valueOf(locationId))
                        .param("visitDate", visitDate.toString()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Then: Verify the data is inserted into the database.
        List<Request> requests = requestRepository.getOpenRequests();

        // Assert: Check if the request was inserted.
        assertTrue(requests.stream().anyMatch(r ->
                r.getUserId().equals(userId) &&
                        r.getLocationId().equals(locationId) &&
                        r.getVisitDate().equals(visitDate)
        ));
    }
}