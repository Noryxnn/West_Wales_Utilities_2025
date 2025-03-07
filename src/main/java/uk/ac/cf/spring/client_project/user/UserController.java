package uk.ac.cf.spring.client_project.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(User user) {
        ModelAndView modelAndView = new ModelAndView("user/registrationForm");
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute User user,
            BindingResult bindingResult,
            @RequestParam(value = "role", defaultValue = "VISITOR") String role) { // Added 'role' parameter
        if (bindingResult.hasErrors()) {
            return "user/registrationForm";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userService.registerUser(user);
            userService.assignRole(user.getEmail(), role); // Assign specified role
            return "user/registrationSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


}