package uk.ac.cf.spring.client_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "uk.ac.cf.spring.client_project")
public class ClientProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientProjectApplication.class, args);
    }
}
