package uk.ac.cf.spring.client_project.location;

import java.util.List;

public interface LocationRepository {
    void save(Location location);

    List<Location> getLocations();

    Location getLocationById(Long id);
}
