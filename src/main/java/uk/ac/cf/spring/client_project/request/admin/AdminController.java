package uk.ac.cf.spring.client_project.request.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.client_project.request.Request;
import uk.ac.cf.spring.client_project.request.RequestService;

import java.util.List;

@Controller
public class AdminController {
    private RequestService requestService;

    public AdminController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/pending-requests")
    public ModelAndView getPendingRequests() {
        ModelAndView modelAndView = new ModelAndView("request/admin/pending-requests");
        List<Request> pendingRequests = requestService.getAllRequests();  // Use the instance method

        System.out.println(pendingRequests);
        modelAndView.addObject("pendingRequests", pendingRequests);
        return modelAndView;
    }


//admin class I created

}