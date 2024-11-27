package uk.ac.cf.spring.client_project.request;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestRepositoryImpl implements RequestRepository {
    private JdbcTemplate jdbc;
    private RowMapper<Request> requestMapper;

    public RequestRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        setRequestMapper();
    }

    private void setRequestMapper() {
        requestMapper = (rs, i) -> new Request(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getLong("location_id"),
                rs.getDate("request_date").toLocalDate(),
                rs.getDate("visit_date").toLocalDate()
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

    public void save(Request aRequest) {
            if (aRequest.isNew()) {
                insert(aRequest);
            } else {
                update(aRequest);
            }
    }

    private void update(Request aRequest) {
        String updateSql = "update requests set user_id = ?, location_id = ?, request_date = ?, visit_date = ? where request_id = ?";
        jdbc.update(updateSql,
                aRequest.getUserId(),
                aRequest.getLocationId(),
                aRequest.getRequestDate(),
                aRequest.getVisitDate()
        );
    }

    private void insert(Request aRequest) {
        String insertSql = "insert into requests(user_id, location_id, request_date, visit_date) values (?,?,?,?)";
        jdbc.update(insertSql,
                aRequest.getUserId(),
                aRequest.getLocationId(),
                aRequest.getRequestDate(),
                aRequest.getVisitDate()
        );
    }

    public boolean userExists(Long userId) {
        String sql = "select count(*) from users where user_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count > 0 && count != null;
    }

    public boolean locationExists(Long locationId) {
        String sql = "select count(*) from locations where location_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, locationId);
        return count > 0 && count != null;
    }
}
