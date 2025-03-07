package uk.ac.cf.spring.client_project.request;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
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
    public ModelAndView request(@Valid @ModelAttribute("request") RequestForm request, BindingResult bindingResult, HttpSession session) {

        ModelAndView modelAndView;

        // Validation checks
        if (request.getVisitDateValidationMessage() != null) {
            bindingResult.rejectValue("visitEndDate", "error.visitEndDate", request.getVisitDateValidationMessage());
        }
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("request/request-form");
        } else {
            // Save the request
            Request newRequest = Request.builder()
                    .visitStartDate(request.getVisitStartDate())
                    .visitEndDate(request.getVisitEndDate())
                    .build();
            Request savedRequest = requestService.save(newRequest);
            session.setAttribute("request", savedRequest);
            modelAndView = new ModelAndView("redirect:/requests/confirmation");
        }
        return modelAndView;
    }

    @GetMapping("/requests/confirmation")
    public ModelAndView requestConfirmation(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("request/confirmation");

        Request request = (Request) session.getAttribute("request");
        if (request != null) {
            modelAndView.addObject("request", request);
            session.removeAttribute("request");
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}

