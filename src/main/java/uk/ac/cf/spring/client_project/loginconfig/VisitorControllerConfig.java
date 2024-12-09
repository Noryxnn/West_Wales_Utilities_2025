package uk.ac.cf.spring.client_project.loginconfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/public")
public class VisitorControllerConfig {
    @GetMapping("/view")
    public String viewPublicPage() {
        return "public/view";
    }
}
