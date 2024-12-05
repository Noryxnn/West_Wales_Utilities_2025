package uk.ac.cf.spring.client_project.location;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getLocations() {
        return locationRepository.getLocations();
    }

    public Location getLocationById(Long id) {
        return locationRepository.getLocationById(id);
    }

    public void save(Location location) {
        locationRepository.save(location);
    }

    public List<LocationType> getLocationTypes() {
        return locationRepository.getLocationTypes();
    }

    public LocationType getLocationTypeById(Long id) {
        return locationRepository.getLocationTypeById(id);
    }

    public void delete(Location location) {
        locationRepository.delete(location);
    }

    public void archive(Location location) {
        locationRepository.archive(location);
    }
    public void deletePermanently(Location menuItem) {
        locationRepository.deletePermanently(menuItem);
    }

    // Scheduled to run every day at midnight GMT.
    // Sourced from: https://www.baeldung.com/spring-scheduled-tasks
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT")
    public void archiveScheduler() {;
        List<Location> deletedLocations = locationRepository.findDeletedLocations();
        for (Location location : deletedLocations) {
            archive(location);
            deletePermanently(location);
        }
    }
}
