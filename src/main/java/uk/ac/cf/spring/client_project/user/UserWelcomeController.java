package uk.ac.cf.spring.client_project.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserWelcomeController {

    @GetMapping("/welcome")
    public String showLoginForm() {
        return "user/landingPage";
    }

}
