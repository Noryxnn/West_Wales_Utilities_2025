package uk.ac.cf.spring.client_project.request;

import java.util.List;

public interface RequestService {
    List<Request> getOpenRequests();
    Request getRequest(Long requestId);
    Request save(Request request);
    boolean validateUserId(Long userId);
}
