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
                rs.getDate("request_date").toLocalDate(),
                rs.getDate("visit_start_date") != null ? rs.getDate("visit_start_date").toLocalDate() : null,
                rs.getDate("visit_end_date") != null ? rs.getDate("visit_end_date").toLocalDate() : null
        );
    }

    public Request getRequest(Long id) {
        String sql = "select * from requests where request_id = ?";
        return jdbc.queryForObject(sql, requestMapper, id);
    }


    /*public List<Request> getOpenRequests() {
        String sql = "select * from requests";
        return jdbc.query(sql, requestMapper);
    } //to fetch pending data*/

    public List<Request> getOpenRequests() {
        String sql = "select * from requests";
        return jdbc.query(sql, (rs, rowNum) -> new Request(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getDate("request_date") != null ? rs.getDate("request_date").toLocalDate() : null,
                rs.getDate("visit_start_date") != null ? rs.getDate("visit_start_date").toLocalDate() : null,
                rs.getDate("visit_end_date") != null ? rs.getDate("visit_end_date").toLocalDate() : null
        ));
    }

    public void save(Request aRequest) {
        if (aRequest.isNew()) {
            insert(aRequest);
        } else {
            update(aRequest);
        }
    }

    private void update(Request aRequest) {
        String updateSql = "update requests set user_id = ?, request_date = ?, visit_start_date = ?,visit_end_date = ? where request_id = ?";
        jdbc.update(updateSql,
                aRequest.getUserId(),
                aRequest.getRequestDate(),
                aRequest.getVisitStartDate(),
                aRequest.getVisitEndDate()
        );
    }

    private void insert(Request aRequest) {
        String insertSql = "insert into requests(user_id, request_date, visit_start_date, visit_end_date) values (?,?,?,?)";
        jdbc.update(insertSql,
                aRequest.getUserId(),
                aRequest.getRequestDate(),
                aRequest.getVisitStartDate(),
                aRequest.getVisitEndDate()
        );
    }

    public boolean userExists(Long userId) {
        String sql = "select count(*) from users where user_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count > 0 && count != null;
    }

    @Override
    public List<Request> findByUserId(Long userId) {
        return null;
    }
}
