package uk.ac.cf.spring.client_project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ENDPOINTS_WHITELIST = {
            "/",
            "/register",
            "/welcome",
            "/error"
    };

    private final DataSource dataSource;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(DataSource dataSource, AuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.dataSource = dataSource;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/oauth2/**", "/login/oauth2/**", "https://accounts.google.com/**"))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staff/**", "api/scan").hasAnyRole("STAFF", "ADMIN")
                        .requestMatchers("/dashboard", "requests/**", "check-in").hasRole("VISITOR")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login") // Manual login page
                        .successHandler(customAuthenticationSuccessHandler) // Use provided custom handler
                        .usernameParameter("email")
                        .permitAll())
                .logout(logout -> logout.permitAll().logoutSuccessUrl("/welcome"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT email AS username, password, enabled FROM users WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT username, authority FROM user_authorities WHERE username = ?");
    }
}