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
    private LocalDate visitDate;

    public Request() { this(0L, 0L, 0L, null, null);
    }

    public boolean isNew() {
        return this.requestId == 0;
    }
}
