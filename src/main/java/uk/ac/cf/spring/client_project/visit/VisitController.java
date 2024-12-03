package uk.ac.cf.spring.client_project.visit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class VisitController {
    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/visits")
    public List<VisitDTO> getCurrentVisits() {
        return visitService.getCurrentlyOnSiteVisits();
    }

    @Controller
    public static class RootController {
        @GetMapping("/")
        public String redirectToVisitTracking() {
            return "redirect:visit/visittracking"; // For static files
        }
    }
}
