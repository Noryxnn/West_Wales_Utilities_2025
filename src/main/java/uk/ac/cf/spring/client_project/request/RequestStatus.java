package uk.ac.cf.spring.client_project.request;

public enum RequestStatus {
    PENDING,    // default enum- for when a request is created and will be pending
    APPROVED,   // When the request is accepted
    DENIED      // When the request is denied
}