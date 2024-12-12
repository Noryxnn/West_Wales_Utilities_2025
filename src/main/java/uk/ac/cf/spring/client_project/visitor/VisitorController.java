package uk.ac.cf.spring.client_project.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.qrcode.QRCodeGenerator;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import uk.ac.cf.spring.client_project.user.UserRepository;

@Controller
public class VisitorController {
    private static final Logger logger = LoggerFactory.getLogger(VisitorController.class);
    UserRepository userRepository;

    public VisitorController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String getDashboard() {
        return "visitor/visitor-dashboard";
    }

    @GetMapping("/access")
    public ModelAndView checkIn() {
        ModelAndView modelAndView = new ModelAndView("visitor/access");
        try {
            // Get current user ID from security context
            Long userId = userRepository.findByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
                    .map(user -> Long.valueOf(user.getUserId()))
                    .orElseThrow(() -> new IllegalStateException("User ID not found"));


            String qrcode = QRCodeGenerator.getQRCode(userId, 300, 300);
            logger.info("Successfully generated QR code for visitor");

            modelAndView.addObject("qrcode", qrcode);

        } catch (Exception e) {
            logger.error("Failed to generate QR code: {}", e.getMessage());
            modelAndView.addObject("error", "Error generating QR code. Please try again.");
        }
        return modelAndView;
    }
}
