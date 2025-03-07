package uk.ac.cf.spring.client_project.location;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        Location location = locationService.getLocationById(id);
        modelAndView.addObject("location", location);

        LocationType locationType = locationService.getLocationTypeById(location.getTypeId());
        modelAndView.addObject("locationType", locationType);

        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView getLocationForm() {
        ModelAndView modelAndView = new ModelAndView("location/location-form");
        LocationForm emptyLocation = new LocationForm();
        modelAndView.addObject("locationForm", emptyLocation);

        List<LocationType> locationTypes = locationService.getLocationTypes();
        modelAndView.addObject("locationTypes", locationTypes);

        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editLocation(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("location/location-form");
        Location locationToUpdate = locationService.getLocationById(id);


        List<LocationType> locationTypes = locationService.getLocationTypes();
        modelAndView.addObject("locationTypes", locationTypes);

        modelAndView.addObject("locationForm", locationToUpdate);
        return modelAndView;
    }

    @PostMapping({"/add", "/edit/{id}"})
    public ModelAndView addLocation(@Valid @ModelAttribute("locationForm") LocationForm location, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("location/location-form", model.asMap());

            List<LocationType> locationTypes = locationService.getLocationTypes();
            modelAndView.addObject("locationTypes", locationTypes);
        } else {
            Location newLocation = new Location(
                    location.getId(),
                    location.getName(),
                    location.getAddressLine1(),
                    location.getAddressLine2(),
                    location.getCity(),
                    location.getPostcode(),
                    location.getTypeId());
            locationService.save(newLocation);
            modelAndView = new ModelAndView("redirect:/admin/locations");
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteLocation(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("location/location-delete");
        Location locationToDelete = locationService.getLocationById(id);
        modelAndView.addObject("location", locationToDelete);
        LocationType locationType = locationService.getLocationTypeById(locationToDelete.getTypeId());
        modelAndView.addObject("locationType", locationType);
        return modelAndView;
    }

    @PostMapping("/delete/confirm/{id}")
    public String deleteConfirmedMenuItem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Location location = locationService.getLocationById(id);
        locationService.delete(location);
        redirectAttributes.addFlashAttribute("message", "Location successfully marked as deleted. Will be moved to archive at midnight.");
        System.out.println("Deleted Location: " + location.getName());
        return "redirect:/admin/locations";
    }
}