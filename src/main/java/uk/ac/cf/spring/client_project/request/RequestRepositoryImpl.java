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
        requestMapper = (rs, i) -> {
            String status = rs.getString("status");

            return new Request(
                    rs.getLong("request_id"),
                    rs.getLong("user_id"),
                    rs.getDate("request_date").toLocalDate().atTime(0, 0),
                    rs.getDate("visit_start_date").toLocalDate(),
                    rs.getDate("visit_end_date").toLocalDate(),
                    RequestStatus.valueOf(status)
            );
        };
    }

    public Request getRequest(Long id) {
        String sql = "select * from requests where request_id = ?";
        return jdbc.queryForObject(sql, requestMapper, id);
    }

    public List<Request> getAllRequests() {
        String sql = "SELECT * FROM requests";
        return jdbc.query(sql, requestMapper);
    }

    //to fetch pending data*/
    public List<Request> getPendingRequests() {
        String sql = "SELECT * FROM requests WHERE status = 'PENDING'";
        return jdbc.query(sql, requestMapper);
    }

    public Request save(Request request) {

        if (request.getStatus() == null) {
            request.setStatus(RequestStatus.PENDING);
        }

        if (request.getRequestId() == null || request.getRequestId() == 0) {

            return insertRequest(request);
        } else {

            return updateRequest(request);
        }
    }

    private Request insertRequest(Request request) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("requests")
                .usingGeneratedKeyColumns("request_id");

        Map<String, Object> columns = new HashMap<>();
        columns.put("user_id", request.getUserId());
        columns.put("request_date", request.getRequestDate());
        columns.put("visit_start_date", request.getVisitStartDate());
        columns.put("visit_end_date", request.getVisitEndDate());
        columns.put("status", request.getStatus().name());

        Number requestId = simpleJdbcInsert.executeAndReturnKey(columns);
        request.setRequestId(requestId.longValue());
        return request;
    }

    private Request updateRequest(Request request) {
        String sql = "UPDATE requests SET status = ?, visit_start_date = ?, visit_end_date = ? WHERE request_id = ?";

        int rowsAffected = jdbc.update(sql,
                request.getStatus().name(),
                request.getVisitStartDate(),
                request.getVisitEndDate(),
                request.getRequestId());

        if (rowsAffected == 0) {

            throw new IllegalArgumentException("Request not found for id: " + request.getRequestId());
        }

        return getRequest(request.getRequestId());
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
