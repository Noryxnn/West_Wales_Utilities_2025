package uk.ac.cf.spring.client_project.request;

import java.util.List;

public interface RequestRepository {
    List<Request> getAllRequests();
    List<Request> getOpenRequests();
    Request getRequest(Long id);
    Request save (Request request);
    boolean userExists(Long userId);
    List<Request> findByUserId(Long userId);
    void acceptRequest(Long requestId);
    void denyRequest(Long requestId);

    List<RequestDTO> findPendingRequests();
}
