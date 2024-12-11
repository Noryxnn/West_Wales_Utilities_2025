package uk.ac.cf.spring.client_project.request;

import java.util.List;

public interface RequestService {
    List<Request> getAllRequests();
    Request save(Request request);
    boolean validateUserId(Long userId);
    void updateRequestStatus(Long id, RequestStatus requestStatus);

    List<Request> getPendingRequests();
}
