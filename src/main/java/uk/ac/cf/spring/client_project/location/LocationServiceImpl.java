package uk.ac.cf.spring.client_project.location;

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

    public void addLocation(Location location) {
        locationRepository.save(location);
    }
}
