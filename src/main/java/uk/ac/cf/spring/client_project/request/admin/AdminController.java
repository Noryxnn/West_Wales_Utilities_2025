package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestService;
import uk.ac.cf.spring.client_project.request.RequestStatus;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RequestService requestService;

    public AdminController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "dashboard/admin-dashboard";
    }


    @GetMapping("/requests")
    public ModelAndView getRequests() {
        ModelAndView modelAndView = new ModelAndView("request/admin/requests");
        List<Request> requests;
        requests = requestService.getAllRequests();
        modelAndView.addObject("requests", requests);

        return modelAndView;
    }

    @GetMapping("request/accept/{id}")
    public ModelAndView acceptRequest(@PathVariable Long id) {
        requestService.updateRequestStatus(id, RequestStatus.APPROVED);
        System.out.println("Request with id " + id + " has been approved!");
        return new ModelAndView("redirect:/admin/requests");
    }

    @GetMapping("request/deny/{id}")
    public ModelAndView denyRequest(@PathVariable Long id) {
        requestService.updateRequestStatus(id, RequestStatus.DENIED);
        System.out.println("Request with id " + id + " has been denied!");
        return new ModelAndView("redirect:/admin/requests");
    }
}