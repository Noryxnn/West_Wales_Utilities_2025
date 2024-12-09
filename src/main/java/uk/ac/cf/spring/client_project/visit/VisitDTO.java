package uk.ac.cf.spring.client_project.visit;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class VisitDTO {
    private Long visitId;
    private Long userId;
    private Long locationId;
    private LocalDateTime checkInDateTime;
    private LocalDateTime checkOutDateTime;

    public VisitDTO() {
        this(0L, 0L, 0L, LocalDateTime.now(), null);
    }
}
