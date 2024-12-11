package uk.ac.cf.spring.client_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;

@Controller
public class RegisterController {
    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("user/registrationForm");
        modelAndView.addObject("user", new Register()); // Provide a new Register object for the form
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password,
            BindingResult bindingResult) {

        // Validate input fields
        if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            bindingResult.rejectValue("email", "error.user", "All fields are required.");
            return "user/registrationForm";
        }

        try {
            // Create a new Register object and populate it
            Register user = new Register();
            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setEmail(email);
            user.setPassword(password);

            // Save the user in the database
            registerService.registerUser(user);

            // Redirect to the login page after successful registration
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.rejectValue("email", "error.user", "Registration failed. Try again.");
            return "user/registrationForm"; // Redirect back to the form on failure
        }
    }
}
