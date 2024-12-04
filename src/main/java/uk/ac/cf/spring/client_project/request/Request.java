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

    private Boolean approved = false;

    public Boolean isApproved() {
        return approved;
    } //getter for boolean

    public void setApproved(Boolean approved) { //setter for boolean
        this.approved = approved;
    }


    /*public Request(Long requestId, @NotNull(message = "User ID is required.") Long userId, LocalDate requestDate, @Future(message = "Visit date must be in the future.") @NotNull(message = "Visit date is required.") LocalDate visitStartDate, LocalDate visitEndDate) { this(0L, 0L, 0L, null, null,null);

    }*/

    public Request(Long aRequestId,Long aUserId,LocalDate aRequestDate,LocalDate aVisitStartDate,LocalDate aVisitEndDate,boolean approved) {
        this(aRequestId,aUserId,0L,aRequestDate,aVisitStartDate,aVisitEndDate,false);
    }




    public boolean isNew() {
        return this.requestId == 0;
    }
}
