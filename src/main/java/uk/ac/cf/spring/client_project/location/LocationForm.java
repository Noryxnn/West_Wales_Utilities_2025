package uk.ac.cf.spring.client_project.location;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationForm {

    private Long id;
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postcode;
    private Long typeId;

    public LocationForm() {
        this(0L, "", "", "", "", "", 0L);
    }
}
