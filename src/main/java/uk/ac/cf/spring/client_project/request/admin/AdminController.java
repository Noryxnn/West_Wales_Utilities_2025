package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.request.Request;

import java.util.List;

@Controller
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;

    }

    @GetMapping("/pending-requests")
    public ModelAndView getPendingRequests() {
        ModelAndView modelAndView = new ModelAndView("request/admin/pending-requests");
        List<Request> pendingRequests = adminService.findPendingRequests();  // Use the instance method

        modelAndView.addObject("pendingRequests", pendingRequests);
        return modelAndView;
    }




}