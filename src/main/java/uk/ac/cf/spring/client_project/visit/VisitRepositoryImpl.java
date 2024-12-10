package uk.ac.cf.spring.client_project.visit;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VisitRepositoryImpl implements VisitRepository {
    private final JdbcTemplate jdbcTemplate;
    @Getter
    private RowMapper<Map<String, Object>> visitDetailsRowMapper;
    private RowMapper<VisitDTO> visitRowMapper;

    @Autowired
    public VisitRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setRowMappers();
    }

    private void setRowMappers() {
        visitRowMapper = (rs, rowNum) -> new VisitDTO(
                rs.getLong("visit_id"),
                rs.getLong("user_id"),
                rs.getLong("location_id"),
                rs.getTimestamp("check_in_datetime").toLocalDateTime(),
                rs.getTimestamp("check_out_datetime") != null
                        ? rs.getTimestamp("check_out_datetime").toLocalDateTime() : null);

        visitDetailsRowMapper = (rs, rowNum) -> {
            Map<String, Object> visitDetails = new HashMap<>();
            visitDetails.put("visitorName", rs.getString("user_first_name") + " " + rs.getString("user_last_name"));
            visitDetails.put("locationName", rs.getString("location_name"));
            visitDetails.put("checkInDate", rs.getTimestamp("check_in_datetime").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            visitDetails.put("checkInTime", rs.getTimestamp("check_in_datetime").toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mma")));
            return visitDetails;
        };
    }

    public List<Map<String, Object>> getCurrentlyOnSiteVisits() {
        String sql = """
                SELECT u.first_name AS user_first_name,
                       u.last_name AS user_last_name,
                       l.name AS location_name,
                       v.check_in_datetime
                FROM visits v
                JOIN users u ON v.user_id = u.user_id
                JOIN locations l ON v.location_id = l.location_id
                                
                WHERE v.check_out_datetime IS NULL
                """;

        return jdbcTemplate.query(sql, visitDetailsRowMapper);
    }

    public void save(VisitDTO visit) {
        String sql = "INSERT INTO visits (user_id, location_id, check_in_datetime) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, visit.getUserId(), visit.getLocationId(), visit.getCheckInDateTime());
    }

    public void update(VisitDTO visit) {
        String sql = "UPDATE visits SET check_out_datetime = ? WHERE visit_id = ?";
        jdbcTemplate.update(sql, visit.getCheckOutDateTime(), visit.getVisitId());
    }

    public VisitDTO getCurrentVisit(Long userId, Long locationId) {
        String sql = "SELECT * FROM visits WHERE user_id = ? AND location_id = ? AND check_in_datetime < NOW() and check_out_datetime IS NULL";
        List<VisitDTO> visits = jdbcTemplate.query(sql, visitRowMapper, userId, locationId);
        return visits.isEmpty() ? null : visits.get(0);    }
}
