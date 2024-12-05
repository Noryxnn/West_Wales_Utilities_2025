package uk.ac.cf.spring.client_project.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @GetMapping("/dashboard")
    public String getStaffDashboard() {
        return "staff/staff-dashboard";
    }
}
