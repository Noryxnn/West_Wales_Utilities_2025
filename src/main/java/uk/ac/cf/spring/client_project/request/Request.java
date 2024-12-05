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
    private boolean isApproved;

    public Request() { this(0L, 0L, LocalDateTime.now(), null, null, false);
    }

    public boolean isNew() {
        return this.requestId == 0;
    }
}
