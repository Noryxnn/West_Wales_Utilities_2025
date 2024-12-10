package uk.ac.cf.spring.client_project.request;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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

            String status = rs.getString("status"); // Replace with the actual column name


            return new Request(
                    rs.getLong("request_id"),
                    rs.getLong("user_id"),
                    rs.getDate("request_date").toLocalDate().atTime(0, 0),
                    rs.getDate("visit_start_date").toLocalDate(),
                    rs.getDate("visit_end_date").toLocalDate(),
                    RequestStatus.valueOf(rs.getString("status"))
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
    public List<Request> getOpenRequests() {
        String sql = "SELECT * FROM requests WHERE status = 'PENDING'";
        return jdbc.query(sql, (rs, rowNum) -> new Request(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getDate("request_date").toLocalDate().atTime(0,0),
                rs.getDate("visit_start_date").toLocalDate(),
                rs.getDate("visit_end_date").toLocalDate(),
                RequestStatus.valueOf(rs.getString("status"))
        ));


    }



    /*public Request save(Request request) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbc)
                .withTableName("requests")
                .usingGeneratedKeyColumns("request_id");
        Map<String, Object> columns = new HashMap<>();
        columns.put("user_id", request.getUserId());
        columns.put("request_date", request.getRequestDate());
        columns.put("visit_start_date", request.getVisitStartDate());
        columns.put("visit_end_date", request.getVisitEndDate());

        Number requestId = simpleJdbcInsert.executeAndReturnKey(columns);
        request.setRequestId(requestId.longValue());
        Request savedRequest = findById(requestId.longValue());


        return request;
    }*/

    public Request save(Request aRequest) {
        if (aRequest.isNew()) {
            insert(aRequest);
        } else {
            update(aRequest);
        }
        return aRequest;
    }


    private void insert(Request aRequest) {
        String insertSql = "insert into requests(user_id, request_date, visit_start_date, visit_end_date,status) values (?,?,?,?,?)";
        jdbc.update(insertSql,
                aRequest.getUserId(),
                aRequest.getRequestDate(),
                aRequest.getVisitStartDate(),
                aRequest.getVisitEndDate(),
                aRequest.getStatus().name()
        );
    }



    private void update(Request aRequest) {
        String updateSql = "UPDATE requests SET user_id = ?, request_date = ?, visit_start_date = ?, visit_end_date = ?, status = ? WHERE request_id = ?";
        jdbc.update(updateSql,
                aRequest.getUserId(),
                aRequest.getRequestDate(),
                aRequest.getVisitStartDate(),

                aRequest.getVisitEndDate(),
                aRequest.getStatus().name(), // Ensure this gets the approved value
                aRequest.getRequestId());
    }



    /*public Request findById(Long requestId) {
        String sql = "select * from requests where request_id = ?";
        return jdbc.queryForObject(sql, requestMapper, requestId);
    }*/

    public boolean userExists(Long userId) {
        String sql = "select count(*) from users where user_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId);
        return count > 0 && count != null;
    }

    public List<Request> findByUserId(Long userId) {
        String sql = "SELECT * FROM requests WHERE user_id = ?";
        return jdbc.query(sql, requestMapper, userId);
    }

    @Override
    public void acceptRequest(Long requestId) {
        String updateSql = "UPDATE requests SET status = 'APPROVED' WHERE request_id = ?";
        jdbc.update(updateSql, requestId);
    }

    @Override
    public void denyRequest(Long requestId) {
        String updateSql = "UPDATE requests SET status = 'DENIED' WHERE request_id = ?";
        jdbc.update(updateSql, requestId);
    }
    @Override
    public List<RequestDTO> findPendingRequests() {
        String sql = """
        SELECT r.request_id, r.user_id, u.first_name, u.last_name, r.request_date, 
               r.visit_start_date, r.visit_end_date, r.status
        FROM requests r
        JOIN users u ON r.user_id = u.user_id
        WHERE r.status = 'PENDING'
    """;

        return jdbc.query(sql, (rs, rowNum) -> new RequestDTO(
                rs.getLong("request_id"),
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getTimestamp("request_date").toLocalDateTime(),
                rs.getDate("visit_start_date").toLocalDate(),
                rs.getDate("visit_end_date").toLocalDate(),
                rs.getString("status")

        ));
    }













}
