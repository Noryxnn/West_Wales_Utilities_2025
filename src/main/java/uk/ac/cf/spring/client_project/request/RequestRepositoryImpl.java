package uk.ac.cf.spring.client_project.request;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RequestRepositoryImpl implements RequestRepository {
    private JdbcTemplate jdbc;
    private RowMapper<Request> requestMapper;

    public RequestRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        setRequestMapper();
    }

    private void setRequestMapper() {
        requestMapper = (rs, i) -> {
            // Map the database column value to the RequestStatus enum
            RequestStatus status = RequestStatus.valueOf(rs.getString("request_status"));  // Enum mapping
            return new Request(
                    rs.getLong("request_id"),
                    rs.getLong("user_id"),
                    rs.getTimestamp("request_date") != null ? rs.getTimestamp("request_date").toLocalDateTime().toLocalDate() : null,
                    rs.getTimestamp("visit_start_date") != null ? rs.getTimestamp("visit_start_date").toLocalDateTime().toLocalDate() : null,
                    rs.getTimestamp("visit_end_date") != null ? rs.getTimestamp("visit_end_date").toLocalDateTime().toLocalDate() : null,
                    status  // Set the request status as the mapped enum
            );
        };
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
        String sql = "SELECT * FROM requests WHERE approved = FALSE";
        return jdbc.query(sql, (rs, rowNum) -> new Request(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getDate("request_date") != null ? rs.getDate("request_date").toLocalDate() : null,
                rs.getDate("visit_start_date") != null ? rs.getDate("visit_start_date").toLocalDate() : null,
                rs.getDate("visit_end_date") != null ? rs.getDate("visit_end_date").toLocalDate() : null,
                RequestStatus.valueOf(rs.getString("approved"))
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
        String updateSql = "UPDATE requests SET user_id = ?, request_date = ?, visit_start_date = ?, visit_end_date = ?, approved = ? WHERE request_id = ?";
        jdbc.update(updateSql,
                aRequest.getUserId(),
                aRequest.getRequestDate(),
                aRequest.getVisitStartDate(),
                aRequest.getVisitEndDate(),
                aRequest.getRequestStatus().name(), // Set approved field as it is
                aRequest.getRequestId()
        );
    }


    private void insert(Request aRequest) {
        String insertSql = "INSERT INTO requests(user_id, request_date, visit_start_date, visit_end_date, approved) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(insertSql,
                aRequest.getUserId(),
                aRequest.getRequestDate(),
                aRequest.getVisitStartDate(),
                aRequest.getVisitEndDate(),
                aRequest.getRequestStatus().name()
        );
    }

    public boolean userExists(Long userId) {
        String sql = "select count(*) from users where user_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count > 0 && count != null;
    }

    @Override
    public Optional<Request> findById(Long requestId) {
        return Optional.empty();
    }

//reference: "https://www.geeksforgeeks.org/optional-empty-method-in-java-with-examples/"


}
