package uk.ac.cf.spring.client_project.request;

import java.util.List;

public interface RequestService {
    void save(Request request);
    boolean validateUserId(Long userId);
}
