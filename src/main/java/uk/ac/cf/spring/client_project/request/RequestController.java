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
        ModelAndView modelAndView = new ModelAndView("request/requestForm");
        RequestForm request = new RequestForm();
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @PostMapping("/request")
    public ModelAndView request(@Valid @ModelAttribute("request") RequestForm request, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView;
        // Check if userId exists in the database
        if (!requestService.validateUserId(request.getUserId())) {
            bindingResult.rejectValue("userId", "userId.invalid", "User ID does not exist.");
        }

        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("request/requestForm", model.asMap());
        } else {
            Request newRequest = new Request(
                    request.getRequestId(),
                    request.getUserId(),
                    request.getRequestDate(),
                    request.getVisitStartDate(),
                    request.getVisitEndDate(),
                    request. isApproved()
            );
            requestService.save(newRequest);
            modelAndView = new ModelAndView("redirect:/request");
        }
        return modelAndView;
    }




}
