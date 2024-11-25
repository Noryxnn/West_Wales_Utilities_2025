package uk.ac.cf.spring.client_project.request;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RequestRepositoryImpl implements RequestRepository {
    private JdbcTemplate jdbc;
    private RowMapper<Request> requestMapper;

    public RequestRepositoryImpl(JdbcTemplate jdbc, RowMapper<Request> requestMapper) {
        this.jdbc = jdbc;
        this.requestMapper = requestMapper;
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
        String menuInsertSql =
                "update requests set user_id = ?, location_id = ?, request_date = ?, visit_date = ? where request_id = ?";
        jdbc.update(menuInsertSql,
                aRequest.getUserId(),
                aRequest.getLocationId(),
                aRequest.getRequestDate(),
                aRequest.getVisitDate()
        );
    }

    private void insert(Request aRequest) {
        String menuInsertSql =
                "insert into requests(user_id, location_id, request_date, visit_date) values (?,?,?,?)";
        jdbc.update(menuInsertSql,
                aRequest.getUserId(),
                aRequest.getLocationId(),
                aRequest.getRequestDate(),
                aRequest.getVisitDate()
        );
    }
}
