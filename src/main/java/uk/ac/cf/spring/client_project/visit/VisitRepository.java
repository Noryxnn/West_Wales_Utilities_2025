package uk.ac.cf.spring.client_project.visit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VisitRepository {
    private final JdbcTemplate jdbcTemplate;

    public VisitRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<VisitDTO> findCurrentlyOnSiteVisits() {
        String sql = """
            SELECT f.first_name AS userName, l.name AS locationName, v.check_in AS checkInTime
            FROM visits v
            JOIN users f ON v.user_id = f.user_id
            JOIN locations l ON v.location_id = l.location_id
            WHERE v.check_out IS NULL
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new VisitDTO(
                rs.getString("userName"),
                rs.getString("locationName"),
                rs.getString("checkInTime")
        ));
    }
}
