package uk.ac.cf.spring.client_project.request;


public interface RequestRepository {
    void save (Request request);
    boolean userExists(Long userId);
}
