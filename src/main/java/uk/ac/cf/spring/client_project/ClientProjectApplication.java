package uk.ac.cf.spring.client_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClientProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientProjectApplication.class, args);
    }

}
