package uk.ac.cf.spring.client_project.staff;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);
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
    public ModelAndView getScanner(@RequestParam("locationId") Long locationId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("staff/qr-scanner");

        if (locationId == null || locationId == 0) {
            logger.error("An invalid location ID was provided when attempting to scan: {}", locationId);
            throw new IllegalArgumentException("A valid location must be selected");
        }
        Location location = locationService.getLocationById(locationId);

        if (location == null) {
            logger.error("An invalid location was provided when attempting to scan: {}", locationId);
            throw new IllegalArgumentException("Location not found with ID: " + locationId);
        }
        session.setAttribute("locationId", locationId);
        modelAndView.addObject("locationName", location.getName());

        return modelAndView;
    }
}
