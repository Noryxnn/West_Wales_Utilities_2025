package uk.ac.cf.spring.client_project.loginconfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControllerConfig {
    @GetMapping("/manage")
    public String manageAdminPage() {
        return "admin/manage";
    }
}

