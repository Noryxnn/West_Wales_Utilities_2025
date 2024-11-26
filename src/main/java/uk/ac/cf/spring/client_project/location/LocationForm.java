package uk.ac.cf.spring.client_project.location;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationForm {

    private Long id;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Address Line 1 is required")
    private String addressLine1;
    @NotEmpty(message = "Address Line 2 is required")
    private String addressLine2;
    @NotEmpty(message = "City is required")
    private String city;
    @NotEmpty(message = "Postcode is required")
    @Pattern(regexp = "^([A-Za-z]{2}\\d{1,2}[A-Za-z]?)\\s+(\\d[A-Za-z]{2})$", message = "Invalid postcode format") // regex from https://docs.linnworks.com/articles/#!documentation/rules-engine-example-regex-uk-postcodes
    private String postcode;
    @NotNull(message = "Location type is required")
    private Long typeId;

    public LocationForm() {
        this(0L, "", "", "", "", "", 0L);
    }
}
