package uk.ac.cf.spring.client_project.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class Request {
    private Long requestId;
    private Long userId;
    private LocalDateTime requestDate;
    private LocalDate visitStartDate;
    private LocalDate visitEndDate;
    private RequestStatus status; //pending by default, approved, denied


    public Request() {
        this(0L, 0L, LocalDateTime.now(), null, null, RequestStatus.PENDING);
    }

    public boolean isNew() {
        return this.requestId == null || this.requestId == 0;
    }
}
