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
    private final RequestService requestService;

    public RequestController(RequestService aRequestService) {
        this.requestService = aRequestService;
    }

    @GetMapping("/requests/new")
    public ModelAndView request() {
        ModelAndView modelAndView = new ModelAndView("request/request-form");
        RequestForm request = new RequestForm();
        modelAndView.addObject("request", request);
        return modelAndView;
    }

    @PostMapping("/requests/new")
    public ModelAndView request(@Valid @ModelAttribute("request") RequestForm request, BindingResult bindingResult, Model model) {
        ModelAndView modelAndView;

        // Check if userId exists in the database
        if (request.getUserId() != null && !requestService.validateUserId(request.getUserId())) {
            bindingResult.rejectValue("userId", "userId.invalid", "User ID does not exist.");
        }

        // Check if visitEndDate is before visitStartDate
        if (request.getVisitDateValidationMessage() != null) {
            bindingResult.rejectValue("visitEndDate", "error.visitEndDate", request.getVisitDateValidationMessage());
        }


        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("request/request-form", model.asMap());
        } else {
            Request newRequest = new Request(
                    request.getRequestId(),
                    request.getUserId(),
                    request.getRequestDate(),
                    request.getVisitStartDate(),
                    request.getVisitEndDate()
            );
            requestService.save(newRequest);
            modelAndView = new ModelAndView("redirect:/requests/new");
        }
        return modelAndView;
    }


}
