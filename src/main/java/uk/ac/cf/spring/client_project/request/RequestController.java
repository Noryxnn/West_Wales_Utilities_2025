package uk.ac.cf.spring.client_project.request;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RequestController {
    private final RequestService requestService;
    private Request request;

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
    public ModelAndView request(@Valid @ModelAttribute("request") RequestForm request, BindingResult bindingResult, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

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
            modelAndView = new ModelAndView("request/request-form");
        } else {
            Request newRequest = new Request(
                    request.getRequestId(),
                    request.getUserId(),
                    request.getRequestDate(),
                    request.getVisitStartDate(),
                    request.getVisitEndDate()
            );

//            requestService.save(newRequest);
//            session.setAttribute("requestId", newRequest.getRequestId());
//            redirectAttributes.addFlashAttribute("request", newRequest);

            Request savedRequest = requestService.save(newRequest);

            session.setAttribute("request", savedRequest);

            request.setRequestId(savedRequest.getRequestId());

            redirectAttributes.addFlashAttribute("request", savedRequest);

            modelAndView = new ModelAndView("redirect:/requests/confirmation");
        }
        return modelAndView;
    }

    @GetMapping("/requests/confirmation")
    public ModelAndView requestConfirmation(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("request/confirmation");
        Long requestId = (Long) session.getAttribute("requestId");
        if (requestId != null) {
            Request request = requestService.findRequestById(requestId);
            if (request != null) {
                modelAndView.addObject("request", request);
            } else {
                modelAndView.setViewName("error");
            }
        } else {
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }

}