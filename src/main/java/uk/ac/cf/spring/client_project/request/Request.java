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
    private Boolean isApproved; // null for pending, true for approved, false for denied


    // Getters and setters
    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        this.isApproved = approved;
    }



    public Request() {this(0L, 0L, LocalDateTime.now(), null, null, null); }
    public boolean isNew() {
        return this.requestId == null || this.requestId == 0;
    }
}
