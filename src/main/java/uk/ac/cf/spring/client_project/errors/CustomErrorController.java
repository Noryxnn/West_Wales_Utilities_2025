package uk.ac.cf.spring.client_project.errors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    // Adapted from https://www.baeldung.com/spring-boot-custom-error-page
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                System.out.println("404 error");
                return "errors/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                System.out.println("500 error");
                return "errors/500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                System.out.println("403 error");
                return "errors/403";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                System.out.println("400 error");
                return "errors/400";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                System.out.println("401 error");
                return "errors/401";
            }
        }
        return "errors/error";
    }
}