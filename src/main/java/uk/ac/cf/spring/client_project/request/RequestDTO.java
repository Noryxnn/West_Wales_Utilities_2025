package uk.ac.cf.spring.client_project.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RequestDTO {
    private Long requestId;
    private Long userId;

    private String firstName;
    private String lastName;
    private LocalDateTime requestDate;
    private LocalDate visitStartDate;
    private LocalDate visitEndDate;
    private RequestStatus status;





    // Constructor
    public RequestDTO(Long requestId, Long userId, String firstName, String lastName, LocalDateTime requestDate,
                      LocalDate visitStartDate, LocalDate visitEndDate, String status ) {
        this.requestId = requestId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.requestDate = requestDate;
        this.visitStartDate = visitStartDate;
        this.visitEndDate = visitEndDate;
        this.status = RequestStatus.valueOf(status);

    }

    // Getters
    public Long getRequestId() {
        return requestId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public LocalDate getVisitStartDate() {
        return visitStartDate;
    }

    public LocalDate getVisitEndDate() {
        return visitEndDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }



    //add logging maybe??
}