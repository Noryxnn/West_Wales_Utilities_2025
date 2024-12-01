package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestRepositoryTests {
    @MockBean
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RequestRepositoryImpl requestRepository;

    @Test
    void shouldSaveNewRequest() {
        // Given
        Request newRequest = new Request();
        newRequest.setUserId(1L);
        newRequest.setRequestDate(LocalDateTime.now());
        // plusDays sourced from: https://www.geeksforgeeks.org/localdate-plusdays-method-in-java-with-examples/
        newRequest.setVisitStartDate(LocalDate.now().plusDays(1));
        newRequest.setVisitEndDate(LocalDate.now().plusDays(5));

        // When
        requestRepository.save(newRequest);

        // Then
        // Verify that the JdbcTemplate's update method was called with the expected SQL and values
        verify(jdbcTemplate).update("insert into requests(user_id, request_date, visit_start_date, visit_end_date) values (?,?,?,?)",
                newRequest.getUserId(),
                newRequest.getRequestDate(),
                newRequest.getVisitStartDate(),
                newRequest.getVisitEndDate()
        );
    }

    @Test
    void shouldCheckIfUserExists() {
        // Given
        Long userId = 1L;
        given(jdbcTemplate.queryForObject("select count(*) from users where user_id = ?", Integer.class, userId))
                .willReturn(1);
        // When
        boolean exists = requestRepository.userExists(userId);
        // Then
        assertTrue(exists);
    }

}
