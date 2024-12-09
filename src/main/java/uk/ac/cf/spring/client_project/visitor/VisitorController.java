package uk.ac.cf.spring.client_project.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.qrcode.QRCodeGenerator;

@Controller
public class VisitorController {
    private static final Logger logger = LoggerFactory.getLogger(VisitorController.class);
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "visitor/visitor-dashboard";
    }

    @GetMapping("/check-in")
    public ModelAndView checkIn() {
        ModelAndView modelAndView = new ModelAndView("visitor/check-in");
        try {
            String qrcode = QRCodeGenerator.getQRCode(300, 300);
            logger.info("Successfully generated QR code for visitor");

            modelAndView.addObject("qrcode", qrcode);

        } catch (Exception e) {
            logger.error("Failed to generate QR code: {}", e.getMessage());
            modelAndView.addObject("error", "Error generating QR code. Please try again.");
        }
        return modelAndView;
    }
}
