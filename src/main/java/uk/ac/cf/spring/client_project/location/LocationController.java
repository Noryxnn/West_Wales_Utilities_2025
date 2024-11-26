package uk.ac.cf.spring.client_project.location;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/locations")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService aLocationService) {
        this.locationService = aLocationService;
    }

    @GetMapping
    public ModelAndView getLocations() {
        ModelAndView modelAndView = new ModelAndView("location/locations");
        modelAndView.addObject("locations", locationService.getLocations());
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getLocation(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("location/location-details");
        Location location = locationService.getLocation(id);
        modelAndView.addObject("location", location);

        return modelAndView;
    }

    @GetMapping("/add-location")
    public ModelAndView getLocationForm() {
        ModelAndView modelAndView = new ModelAndView("location/location-form");
        LocationForm emptyLocation = new LocationForm();
        modelAndView.addObject("locationForm", emptyLocation);

        return modelAndView;
    }

    @PostMapping("/add-location")
    public ModelAndView addLocation(@Valid @ModelAttribute("locationForm") LocationForm location, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("location/location-form", model.asMap());
        } else {
            Location newLocation = new Location(
                    location.getId(),
                    location.getName(),
                    location.getAddressLine1(),
                    location.getAddressLine2(),
                    location.getCity(),
                    location.getPostcode(),
                    location.getTypeId());

            locationService.addLocation(newLocation);
            modelAndView = new ModelAndView("redirect:/admin/locations");
        }
        return modelAndView;
    }
}
