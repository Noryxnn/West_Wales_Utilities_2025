package uk.ac.cf.spring.client_project.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VisitRepositoryTests {
    @Mock
    private JdbcTemplate jdbcTemplate;
    private VisitRepositoryImpl visitRepository;

    @BeforeEach
    void setUp() {
        visitRepository = new VisitRepositoryImpl(jdbcTemplate);
    }

    @Test
    void testGetCurrentlyOnSiteVisits() {
        String mockSQL = """
                SELECT u.first_name AS user_first_name,
                       u.last_name AS user_last_name,
                       l.name AS location_name,
                       v.check_in_datetime
                FROM visits v
                JOIN users u ON v.user_id = u.user_id
                JOIN locations l ON v.location_id = l.location_id
                
                WHERE v.check_out_datetime IS NOT NULL
                """;

        when(jdbcTemplate.query(mockSQL, visitRepository.getVisitDetailsRowMapper())).thenReturn(List.of(
                Map.of(
                        "visitorName", "John Doe",
                        "locationName", "Building A",
                        "checkInDate", "08/12/2024",
                        "checkInTime", "10:30AM"
                )
        ));

        List<Map<String, Object>> result = visitRepository.getCurrentlyOnSiteVisits();

        assertEquals(1, result.size());  // Ensure we have one entry
        assertEquals("John Doe", result.get(0).get("visitorName"));
        assertEquals("Building A", result.get(0).get("locationName"));
        assertEquals("08/12/2024", result.get(0).get("checkInDate"));
        assertEquals("10:30AM", result.get(0).get("checkInTime"));
    }
}
