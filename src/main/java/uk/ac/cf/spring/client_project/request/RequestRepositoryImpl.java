package uk.ac.cf.spring.client_project.request;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RequestRepositoryImpl implements RequestRepository {
    private final JdbcTemplate jdbc;
    private RowMapper<Request> requestMapper;

    public RequestRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        setRequestMapper();
    }

    private void setRequestMapper() {
        requestMapper = (rs, i) -> new Request(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getDate("request_date").toLocalDate().atTime(0, 0),
                rs.getDate("visit_start_date").toLocalDate(),
                rs.getDate("visit_end_date").toLocalDate(),
                rs.getBoolean("is_approved")
        );
    }

    public Request getRequest(Long id) {
        String sql = "select * from requests where request_id = ?";
        return jdbc.queryForObject(sql, requestMapper, id);
    }


    public List<Request> getOpenRequests() {
        String sql = "select * from requests";
        return jdbc.query(sql, requestMapper);
    }


    public Request save(Request request) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("requests")
                .usingGeneratedKeyColumns("request_id");
        Map<String, Object> columns = new HashMap<>();
        columns.put("user_id", request.getUserId());
        columns.put("request_date", request.getRequestDate());
        columns.put("visit_start_date", request.getVisitStartDate());
        columns.put("visit_end_date", request.getVisitEndDate());

        try {
            Number requestId = simpleJdbcInsert.executeAndReturnKey(columns);
            request.setRequestId(requestId.longValue());
        } catch (Exception e) {
            System.err.println("Error inserting request: " + e.getMessage());
            throw e;
        }

        Request savedRequest = findById(request.getRequestId());
        System.out.println("Request saved: " + savedRequest);

        return request;
    }

    public Request findById(Long requestId) {
        String sql = "select * from requests where request_id = ?";
        return jdbc.queryForObject(sql, requestMapper, requestId);
    }

    public boolean userExists(Long userId) {
        String sql = "select count(*) from users where user_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count > 0 && count != null;
    }

    public List<Request> findByUserId(Long userId) {
        String sql = "SELECT * FROM requests WHERE user_id = ?";
        return jdbc.query(sql, requestMapper, userId);
    }
}
