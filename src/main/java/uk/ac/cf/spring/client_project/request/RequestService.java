package uk.ac.cf.spring.client_project.request;


public interface RequestService {
    Request save(Request request);
    boolean validateUserId(Long userId);
    Request findById(Long requestId);
}
