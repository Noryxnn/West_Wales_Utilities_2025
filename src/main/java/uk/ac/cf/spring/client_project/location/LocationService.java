package uk.ac.cf.spring.client_project.location;

import java.util.List;


public interface LocationService {
    void save(Location location);

    List<Location> getLocations();

    Location getLocationById(Long id);

    List<LocationType> getLocationTypes();

    LocationType getLocationTypeById(Long id);

    void delete(Location location);

    void archive(Location location);

    void deletePermanently(Location location);

    void archiveScheduler();

}
