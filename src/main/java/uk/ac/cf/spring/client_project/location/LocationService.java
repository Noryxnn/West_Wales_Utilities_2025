package uk.ac.cf.spring.client_project.location;

import java.util.List;

public interface LocationService {
    void addLocation(Location location);

    List<Location> getLocations();

    Location getLocationById(Long id);

    List<LocationType> getLocationTypes();

    LocationType getLocationTypeById(Long id);
}
