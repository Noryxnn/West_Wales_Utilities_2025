package uk.ac.cf.spring.client_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Request {
    private Long requestId;
    private Long userId;
    private LocalDateTime requestDate;
    private LocalDate visitStartDate;
    private LocalDate visitEndDate;

    private RequestStatus status; //pending by default, approved, denied


    // getter and setter for status
    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    public Request() {
        this(0L, 0L, LocalDateTime.now(), null, null, RequestStatus.PENDING);
    }




    public boolean isNew() {
        return this.requestId == null || this.requestId == 0;
    }
}
