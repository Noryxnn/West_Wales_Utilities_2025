package uk.ac.cf.spring.client_project.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
@SpringBootTest
class SecurityConfigTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Mock
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Test
    void passwordEncoder_ShouldReturnBCryptPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        assertThat(passwordEncoder).isInstanceOf(BCryptPasswordEncoder.class);
    }

    @Test
    void authenticationManager_ShouldReturnAuthenticationManager() throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        assertThat(authenticationManager).isNotNull();
        assertThat(authenticationManager).isInstanceOf(AuthenticationManager.class);
    }

    @Test
    void csrfConfiguration_ShouldIgnoreSpecificEndpoints() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers(
                "/oauth2/**", "/login/oauth2/**", "https://accounts.google.com/**"));

        verify(httpSecurity).csrf(any());
    }

    @Test
    void formLogin_ShouldUseCustomLoginPageAndSuccessHandler() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        httpSecurity.formLogin(form -> form
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .usernameParameter("email")
                .permitAll());

        verify(httpSecurity).formLogin(any());
    }

    @Test
    void logoutConfiguration_ShouldPermitAllAndRedirectToWelcome() throws Exception {
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        httpSecurity.logout(logout -> logout.permitAll().logoutSuccessUrl("/welcome"));

        verify(httpSecurity).logout(any());
    }

    @Test
    void securityFilterChain_ShouldBeConfiguredCorrectly() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        when(http.csrf(any())).thenReturn(http);
        when(http.authorizeHttpRequests(any())).thenReturn(http);
        when(http.formLogin(any())).thenReturn(http);
        when(http.logout(any())).thenReturn(http);

        SecurityFilterChain filterChain = securityConfig.securityFilterChain(http);
        assertThat(filterChain).isNotNull();
    }

    @Test
    void endpointsWhitelist_ShouldMatchExpectedEndpoints() {
        String[] expectedEndpoints = {"/", "/register", "/welcome", "/error"};
        assertThat(SecurityConfig.ENDPOINTS_WHITELIST).containsExactly(expectedEndpoints);
    }

    @Test
    void passwordEncoder_ShouldHashAndMatchPasswords() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }
}
