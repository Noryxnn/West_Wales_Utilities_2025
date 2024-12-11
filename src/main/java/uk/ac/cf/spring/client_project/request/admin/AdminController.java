package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.request.Request;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "dashboard/admin-dashboard";
    }

    @GetMapping("/pending-requests")
    public ModelAndView getPendingRequests() {
        ModelAndView modelAndView = new ModelAndView("/request/admin/pending-requests");
        modelAndView.addObject("pendingRequests", adminService.findPendingRequests());
        return modelAndView;
    }
}
