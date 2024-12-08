package uk.ac.cf.spring.client_project.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.qrcode.QRCodeGenerator;

@Controller
public class VisitorController {
    @GetMapping("/dashboard")
    public String getDashboard() {
        return "visitor/visitor-dashboard";
    }

    @GetMapping("/check-in")
    public ModelAndView checkIn() {
        ModelAndView modelAndView = new ModelAndView("visitor/check-in");
        try {
            String qrcode = QRCodeGenerator.getQRCode(300, 300);
            modelAndView.addObject("qrcode", qrcode);
        } catch (Exception e) {
            System.out.println("Error generating QR code: " + e.getMessage());
            modelAndView.addObject("error", "Error generating QR code. Please try again.");
        }
        return modelAndView;
    }
}
