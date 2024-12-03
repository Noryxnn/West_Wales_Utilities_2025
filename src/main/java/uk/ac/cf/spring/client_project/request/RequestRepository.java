package uk.ac.cf.spring.client_project.request;

import java.util.List;

public interface RequestRepository {
    List<Request> getOpenRequests();
    Request getRequest(Long id);
    void save (Request request);
    boolean userExists(Long userId);
}
