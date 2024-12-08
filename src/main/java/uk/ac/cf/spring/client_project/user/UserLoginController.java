package uk.ac.cf.spring.client_project.user;

import org.springframework.beans.factory.annotation.Autowired;
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

    // Display the login form when the user accesses the /login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "user/loginForm"; // Render the login form (loginForm.html)
    }

    // Process the login form when submitted via POST
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        boolean isAuthenticated = userLoginService.authenticateUser(email, password);

        if (isAuthenticated) {
            // If authentication is successful, redirect to the success page
            return "redirect:/loginSuccess"; // Redirect to the login success page
        } else {
            // If authentication fails, show an error message
            model.addAttribute("error", "Account not found or incorrect credentials.");
            return "user/loginForm"; // Return to the login form with error message
        }
    }

    // Show the login success page when the user is redirected after successful login
    @GetMapping("/loginSuccess")
    public String showLoginSuccess() {
        return "user/loginSuccess";
    }
}
