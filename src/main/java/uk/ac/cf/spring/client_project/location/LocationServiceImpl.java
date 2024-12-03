package uk.ac.cf.spring.client_project.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service


public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(@Lazy LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> getLocations() {
        return locationRepository.getLocations();
    }

    public Location getLocationById(Long id) {
        return locationRepository.getLocationById(id);
    }

    public void addLocation(Location location) {
        locationRepository.save(location);
    }

    public List<LocationType> getLocationTypes() {
        return locationRepository.getLocationTypes();
    }

    public LocationType getLocationTypeById(Long id) {
        return locationRepository.getLocationTypeById(id);
    }
}
