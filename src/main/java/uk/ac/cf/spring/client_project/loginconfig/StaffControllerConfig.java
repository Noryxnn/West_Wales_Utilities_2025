package uk.ac.cf.spring.client_project.loginconfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffControllerConfig {
    @GetMapping("/dashboard")
    public String viewStaffDashboard() {
        return "staff/dashboard";
    }
}

