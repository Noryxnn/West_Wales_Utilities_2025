package uk.ac.cf.spring.client_project.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.location.Location;
import uk.ac.cf.spring.client_project.location.LocationService;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    LocationService locationService;

    @Autowired
    public StaffController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/dashboard")
    public ModelAndView getStaffDashboard() {
        ModelAndView modelAndView = new ModelAndView("staff/staff-dashboard");
        List<Location> locations = locationService.getLocations();
        modelAndView.addObject("locations", locations);

        return modelAndView;
    }

    @PostMapping("/scan")
    public ModelAndView getScanner(@RequestParam("locationId") Long locationId) {
        ModelAndView modelAndView = new ModelAndView("staff/qr-scanner");

        if (locationId == null || locationId == 0) {
            throw new IllegalArgumentException("A valid location must be selected");
        }
        Location location = locationService.getLocationById(locationId);

        if (location == null) {
            throw new IllegalArgumentException("Location not found with ID: " + locationId);
        }
        modelAndView.addObject("location", location);

        return modelAndView;
    }
}
