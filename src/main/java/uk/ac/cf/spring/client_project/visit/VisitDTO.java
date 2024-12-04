package uk.ac.cf.spring.client_project.visit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitDTO {
    private String userName;
    private String locationName;
    private String checkInTime;
    public VisitDTO(long l, String userName, String locationName, String checkInTime, long l1) {

    }
}
