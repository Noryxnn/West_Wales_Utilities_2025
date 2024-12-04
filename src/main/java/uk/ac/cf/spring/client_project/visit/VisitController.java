package uk.ac.cf.spring.client_project.visit;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/visits")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public ModelAndView getAllVisits() {
        ModelAndView modelAndView = new ModelAndView("visit/visittracking"); // Include subdirectory
        modelAndView.addObject("visits", visitService.getCurrentlyOnSiteVisits());
        return modelAndView;
    }
}
