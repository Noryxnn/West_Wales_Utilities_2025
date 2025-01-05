package uk.ac.cf.spring.client_project.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginController {

    // Dashboard for Google login users
    @GetMapping("/visitor/dashboard")
    public String visitorDashboard(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
        model.addAttribute("name", oauth2User.getAttribute("name"));
        model.addAttribute("email", oauth2User.getAttribute("email"));
        return "/visitor/visitor-dashboard"; // Visitor dashboard template
    }

    // Google login page endpoint
    @GetMapping("/oauth2-login")
    public String loginPage() {
        return "user/loginForm"; // Use the existing loginForm template
    }
}
