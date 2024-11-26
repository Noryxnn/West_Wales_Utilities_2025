package uk.ac.cf.spring.client_project.request;

import java.util.List;

public interface RequestService {
    List<Request> getOpenRequests();
    Request getRequest(Long requestId);
    void save(Request request);
    boolean validateUserId(Long userId); // New method
    boolean validateLocationId(Long locationId); // New method
}
