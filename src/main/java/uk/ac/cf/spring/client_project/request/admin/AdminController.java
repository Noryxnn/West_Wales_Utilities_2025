package uk.ac.cf.spring.client_project.request.admin;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestForm;
import uk.ac.cf.spring.client_project.request.RequestService;

import java.util.List;

@Controller
public class AdminController {
    private AdminService adminService;
    private final RequestService requestService;

    public AdminController(AdminService adminService, RequestService requestService) {
        this.adminService = adminService;
        this.requestService = requestService;

    }



    @GetMapping("/pending-requests")
    public ModelAndView getPendingRequests() {
        ModelAndView modelAndView = new ModelAndView("request/admin/pending-requests");
        List<Request> pendingRequests = adminService.findPendingRequests();  // Use the instance method

        modelAndView.addObject("pendingRequests", pendingRequests);
        return modelAndView;
    }


//admin class I created

    @GetMapping("/admin/requests/new")
    public ModelAndView request() {
        ModelAndView modelAndView = new ModelAndView("request/admin/request-form");
        RequestForm request = new RequestForm();
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @PostMapping("/admin/requests/new")
    public ModelAndView request(@Valid @ModelAttribute("request") RequestForm request, BindingResult bindingResult) {
        ModelAndView modelAndView;
        // Validation checks
        if (request.getUserId() != null && !requestService.validateUserId(request.getUserId())) {
            bindingResult.rejectValue("userId", "userId.invalid", "User ID does not exist.");
        }
        if (request.getVisitDateValidationMessage() != null) {
            bindingResult.rejectValue("visitEndDate", "error.visitEndDate", request.getVisitDateValidationMessage());
        }
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("request/admin/request-form");
        } else {
            // Save the request
            Request newRequest = new Request(
                    request.getRequestId(),
                    request.getUserId(),
                    request.getRequestDate(),
                    request.getVisitStartDate(),
                    request.getVisitEndDate(),
                    true
            );
            Request savedRequest = requestService.save(newRequest);
            modelAndView = new ModelAndView("redirect:/admin/requests/confirmation");
        }
        return modelAndView;
    }

    @GetMapping("/admin/requests/confirmation/{id}")
    public ModelAndView confirmation(@PathVariable("id") Long id) {
        Request savedRequest = requestService.getRequest(id);
        ModelAndView modelAndView = new ModelAndView("request/admin/confirmation");
        modelAndView.addObject("request", savedRequest);
        return modelAndView;
    }
}