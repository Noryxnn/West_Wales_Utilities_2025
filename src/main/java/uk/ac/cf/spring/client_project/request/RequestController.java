package uk.ac.cf.spring.client_project.request;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RequestController {
    private RequestService requestService;

    public RequestController(RequestService aRequestService) {
        this.requestService = aRequestService;
    }

    @GetMapping("/request")
    public ModelAndView request() {
        ModelAndView modelAndView = new ModelAndView("menu/menuItemFormTh");
        RequestForm request = new RequestForm();
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @PostMapping("/request")
    public ModelAndView request(@Valid @ModelAttribute("request") RequestForm request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("request/requestForm", model.asMap());
        } else {
            ModelAndView modelAndView;
            Request newRequest = new Request(
                    request.getRequestId(),
                    request.getUserId(),
                    request.getLocationId(),
                    request.getRequestDate(),
                    request.getVisitDate());
            requestService.save(newRequest);
            modelAndView = new ModelAndView("requestForm");
            modelAndView.addObject("request", request);
            return modelAndView;
        }
    }
}
