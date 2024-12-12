package uk.ac.cf.spring.client_project.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    // Adapted from: https://medium.com/java-epic/spring-security-redirect-based-on-user-roles-b13c22050a64
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/dashboard");
        } else if (roles.contains("ROLE_STAFF")) {
            httpServletResponse.sendRedirect("/staff/dashboard");
        } else {
            httpServletResponse.sendRedirect("/dashboard");
        }
    }
}
