package uk.ac.cf.spring.client_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Request {
    private Long requestId;
    private Long userId;
    private Long locationId;
    private LocalDate requestDate;
    private LocalDate visitStartDate;
    private LocalDate visitEndDate;

    private RequestStatus requestStatus;

    // Default constructor can set approvalStatus to PENDING if you like
    // Getters and setters for requestStatus
    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
    public Request(Long requestId, Long userId, LocalDate requestDate, LocalDate visitStartDate, LocalDate visitEndDate, RequestStatus requestStatus) {
        this(requestId, userId, 0L, requestDate, visitStartDate, visitEndDate, RequestStatus.PENDING);
    }

    public boolean isNew() {
        return this.requestId == 0;
    }
}
