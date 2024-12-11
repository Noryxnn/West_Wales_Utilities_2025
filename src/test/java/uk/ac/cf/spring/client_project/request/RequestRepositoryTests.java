package uk.ac.cf.spring.client_project.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestRepositoryTests {
    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RequestRepositoryImpl requestRepository;

    @Test
    void shouldCheckIfUserExists() {
        // Given
        Long userId = 1L;
        given(jdbcTemplate.queryForObject("select count(*) from users where user_id = ?", Integer.class, userId))
                .willReturn(1);
        // When
        boolean exists = requestRepository.userExists(userId);

        assertTrue(exists);
    }

    @Test
    void shouldFindRequestById() {
        Long requestId = 1L;
        Request expectedRequest = new Request(
                requestId,
                1L,
                LocalDateTime.of(2023, 12, 1, 0, 0),
                LocalDate.of(2023, 12, 2),
                LocalDate.of(2023, 12, 5),
                RequestStatus.APPROVED
        );

        RowMapper<Request> rowMapper = (rs, rowNum) -> new Request(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getTimestamp("request_date").toLocalDateTime(),
                rs.getDate("visit_start_date").toLocalDate(),
                rs.getDate("visit_end_date").toLocalDate(),
                RequestStatus.valueOf(rs.getString("is_approved").toUpperCase())
        );

        given(jdbcTemplate.queryForObject(eq("select * from requests where request_id = ?"), eq(rowMapper), eq(requestId))).willReturn(expectedRequest);
    }
}