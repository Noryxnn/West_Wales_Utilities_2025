package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.client_project.user.User;
import uk.ac.cf.spring.client_project.user.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminUserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/add-user")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "request/admin/addingusers";
    }

    @PostMapping("/add-user")
    public String addUser(@Valid @ModelAttribute User user, BindingResult bindingResult, @RequestParam("role") String role) {
        if (bindingResult.hasErrors()) {
            return "request/admin/addingusers";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userService.registerUser(user);

        // Assign role
        if ("ADMIN".equalsIgnoreCase(role)) {
            userService.assignRole(user.getEmail(), "ADMIN");
        } else {
            userService.assignRole(user.getEmail(), "STAFF");
        }

        return "redirect:/admin/dashboard";
    }
}

