package uk.ac.cf.spring.client_project.request;


public interface RequestRepository {
    Request save (Request request);
    boolean userExists(Long userId);
    Request findById(Long requestId);
}
