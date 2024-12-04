package uk.ac.cf.spring.client_project.visitor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.qrcode.QRCodeGenerator;

@Controller
public class VisitorController {
    @GetMapping("/dashboard")
    public ModelAndView getDashboard() {
        ModelAndView modelAndView = new ModelAndView("visitor/visitor-dashboard");
        try {
            String qrcode = QRCodeGenerator.getQRCode(250, 250);
            modelAndView.addObject("qrcode", qrcode);
        } catch (Exception e) {
            modelAndView.addObject("error", "Error generating QR code: " + e.getMessage());
        }
        return modelAndView;
    }
}
