package uk.ac.cf.spring.client_project.request;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

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
        if (request.getUserId() != null && !requestService.validateUserId(request.getUserId())) {
            bindingResult.rejectValue("userId", "userId.invalid", "User ID does not exist.");
        }
        if (request.getVisitDateValidationMessage() != null) {
            bindingResult.rejectValue("visitEndDate", "error.visitEndDate", request.getVisitDateValidationMessage());
        }
        if (bindingResult.hasErrors()) {
            modelAndView = new ModelAndView("request/request-form");
        } else {
            // Save the request
            Request newRequest = new Request(
                    request.getRequestId(),
                    request.getUserId(),
                    LocalDateTime.now(),
                    request.getVisitStartDate(),
                    request.getVisitEndDate(),
                    RequestStatus.PENDING
            );
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
//            session.removeAttribute("request");
        } else {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @GetMapping("request/accept/{id}")
    public ModelAndView acceptRequest(@PathVariable Long id) {
        requestService.updateRequestStatus(id, RequestStatus.APPROVED);
        System.out.println("Request with id " + id + " has been approved!");
        return new ModelAndView("redirect:/pending-requests");
    }

    @GetMapping("request/deny/{id}")
    public ModelAndView denyRequest(@PathVariable Long id) {
        requestService.updateRequestStatus(id, RequestStatus.DENIED);
        System.out.println("Request with id " + id + " has been denied!");
        return new ModelAndView("redirect:/pending-requests");
    }







}
