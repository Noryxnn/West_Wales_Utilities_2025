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
    private boolean isApproved;


    /*public Request(Long requestId, @NotNull(message = "User ID is required.") Long userId, LocalDate requestDate, @Future(message = "Visit date must be in the future.") @NotNull(message = "Visit date is required.") LocalDate visitStartDate, LocalDate visitEndDate) { this(0L, 0L, 0L, null, null,null);

    }*/

    public Request(Long aRequestId,Long aUserId,LocalDate aRequestDate,LocalDate aVisitStartDate,LocalDate aVisitEndDate) {
        this(aRequestId,aUserId,0L,aRequestDate,aVisitStartDate,aVisitEndDate);
    }





    public boolean isNew() {
        return this.requestId == 0;
    }
}
