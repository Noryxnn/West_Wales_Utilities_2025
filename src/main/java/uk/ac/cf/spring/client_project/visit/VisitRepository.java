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

    private final RowMapper<VisitDTO> rowMapper = (rs, rowNum) -> new VisitDTO(
            rs.getString("first_name"),
            rs.getString("location_name"),
            rs.getString("check_in")
    );

    public List<VisitDTO> findCurrentlyOnSiteVisits() {
        String sql = """
            SELECT f.first_name AS first_name, l.name AS location_name, v.check_in
            FROM visits v
            JOIN users f ON v.user_id = f.user_id
            JOIN locations l ON v.location_id = l.location_id
            WHERE v.check_out IS NULL
        """;
        return jdbcTemplate.query(sql, rowMapper);
    }
}
