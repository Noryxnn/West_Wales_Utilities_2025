package uk.ac.cf.spring.client_project.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
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


    public Request(Long requestId, @NotNull(message = "User ID is required.") Long userId, LocalDate requestDate, @Future(message = "Visit date must be in the future.") @NotNull(message = "Visit date is required.") LocalDate visitStartDate, LocalDate visitEndDate) { this(0L, 0L, 0L, null, null,null);
    }

    public Request(long requestId, long userId, long locationId, LocalDate requestDate, LocalDate localDate, LocalDate localDate1) {

    }

    public boolean isNew() {
        return this.requestId == 0;
    }
}
