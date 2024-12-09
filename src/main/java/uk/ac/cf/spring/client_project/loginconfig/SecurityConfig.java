package uk.ac.cf.spring.client_project.loginconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        // Public access
                        .requestMatchers("/images/**", "/", "/403", "/order/**", "/menu/**", "/public/**").permitAll()
                        .requestMatchers("/login", "/register", "/requests/**").permitAll()

                        // Admin access
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // Staff access
                        .requestMatchers("/staff/**").hasRole("STAFF")

                        // QR controller, assuming staff handles it
                        .requestMatchers("/scan").hasRole("STAFF")

                        // Any other request needs authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/")
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/403")
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcUserDetails = new JdbcDaoImpl();
        jdbcUserDetails.setDataSource(dataSource);
        jdbcUserDetails.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
        jdbcUserDetails.setAuthoritiesByUsernameQuery("SELECT username, authority FROM user_authorities WHERE username = ?");
        return jdbcUserDetails;
    }
}


