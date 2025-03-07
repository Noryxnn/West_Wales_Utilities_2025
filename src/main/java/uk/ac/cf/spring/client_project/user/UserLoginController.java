package uk.ac.cf.spring.client_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserLoginController {
    private final UserLoginService userLoginService;

    @Autowired
    public UserLoginController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/loginForm";
    }

    @Autowired
    public AuthenticationManager manager;

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {

        boolean isAuthenticated = userLoginService.authenticateUser(email, password);

        if (isAuthenticated) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Account not found or incorrect credentials.");
            return "user/loginForm"; // Return to the login form with error message
        }
    }
}
