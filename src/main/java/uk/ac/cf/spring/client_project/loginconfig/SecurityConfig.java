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
                            .requestMatchers("/images/**", "/", "/403", "/order/**", "/menu/**").permitAll()
                            .requestMatchers("/login", "/register", "/requests/**").permitAll()
                            .requestMatchers("/dashboard", "/check-in").permitAll()

                            // Admin access
                            .requestMatchers("/admin/**", "/admin/locations/**", "/admin/visits/**").hasRole("ADMIN")

                            // Staff access
                            .requestMatchers("/staff/**", "/scan").hasRole("STAFF")

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

            // Query to fetch user details
            jdbcUserDetails.setUsersByUsernameQuery(
                    "SELECT email AS username, password, true AS enabled FROM users WHERE email = ?"
            );


            // Query to fetch user roles
            jdbcUserDetails.setAuthoritiesByUsernameQuery(
                    "SELECT u.email AS username, r.role_name AS authority " +
                            "FROM users u " +
                            "JOIN user_roles ur ON u.user_id = ur.user_id " +
                            "JOIN roles r ON ur.role_id = r.role_id " +
                            "WHERE u.email = ?"
            );


            return jdbcUserDetails;
        }
    }



