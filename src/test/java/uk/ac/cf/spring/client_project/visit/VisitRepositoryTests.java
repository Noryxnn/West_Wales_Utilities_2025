package uk.ac.cf.spring.client_project.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VisitRepositoryTests {
    private VisitRepository visitRepository;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        visitRepository = new VisitRepository(jdbcTemplate);
    }

    @Test
    void testFindCurrentlyOnSiteVisits() {
        String mockSQL = """
            SELECT
                u.first_name AS userName,
                l.name AS locationName,
                v.check_in AS checkInTime
            FROM
                visits v
            JOIN
                users u ON v.user_id = u.user_id
            JOIN
                locations l ON v.location_id = l.location_id
        """;

        VisitDTO mockVisit = new VisitDTO("John", "Office", "2024-12-04T10:00:00");
        when(jdbcTemplate.query(eq(mockSQL), any(RowMapper.class)))
                .thenReturn(List.of(mockVisit));

        List<VisitDTO> result = visitRepository.findCurrentlyOnSiteVisits();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getUserName());
    }
}
