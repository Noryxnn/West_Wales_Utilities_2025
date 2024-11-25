package uk.ac.cf.spring.client_project.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RequestForm {
    private Long requestId;
    @NotNull(message = "User ID field cannot be empty.")
    private Long userId;
    @NotNull (message = "Location ID field cannot be empty.")
    private Long locationId;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate requestDate;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Future (message = "Visit date must be in the future.")
    private LocalDate visitDate;

    public RequestForm() { this(0L, 0L, 0L, null, null);
    }

}
