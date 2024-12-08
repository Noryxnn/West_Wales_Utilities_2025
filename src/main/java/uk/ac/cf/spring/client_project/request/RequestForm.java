package uk.ac.cf.spring.client_project.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RequestForm {
    private Long requestId;
    @NotNull(message = "User ID is required.")
    private Long userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime requestDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future (message = "Visit date must be in the future.")
    @NotNull(message = "Visit start date is required.")
    private LocalDate visitStartDate;
    @NotNull(message = "Visit end date is required.")
    private LocalDate visitEndDate;
    private Boolean isApproved;

    public RequestForm() { this(0L, 0L,  LocalDateTime.now(), null,null, null);
    }

    public String getVisitDateValidationMessage() {
        // Checks if visitEndDate is before visitStartDate
        // isBefore() sourced from: https://www.geeksforgeeks.org/localdate-isbefore-method-in-java-with-examples/
        if (visitStartDate != null && visitEndDate != null && !(visitStartDate.isBefore(visitEndDate) || visitStartDate.isEqual(visitEndDate))) {
            return "Visit end date must be on or after visit start date.";
        } else {
            return null;
        }
    }

    public Boolean isApproved() {
        return null;
    }
}
