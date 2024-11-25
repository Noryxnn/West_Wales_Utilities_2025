package uk.ac.cf.spring.client_project.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestForm {
    private Long requestId;
    @NotEmpty (message = "User ID field cannot be empty.")
    private Long userId;
    @NotEmpty (message = "Location field cannot be empty.")
    private Long locationId;
    private LocalDate requestDate;
    @Future (message = "Visit date must be in the future.")
    private LocalDate visitDate;

    public RequestForm() { this(null, 0L, 0L, LocalDate.now(), null);
    }

}
