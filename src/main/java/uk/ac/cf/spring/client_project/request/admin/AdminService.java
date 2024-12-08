package uk.ac.cf.spring.client_project.request.admin;

import uk.ac.cf.spring.client_project.request.Request;

import java.util.List;

public interface AdminService {

    /**
     * Find all pending requests.
     *
     * @return a list of pending requests.
     */
    List<Request> findPendingRequests();

}

//adminservice class