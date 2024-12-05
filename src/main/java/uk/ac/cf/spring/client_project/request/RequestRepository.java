package uk.ac.cf.spring.client_project.request;


import java.util.List;

public interface RequestRepository {
    Request save (Request request);
    boolean userExists(Long userId);
    Request findById(Long requestId);
    List<Request> findByUserId(Long userId);
}
