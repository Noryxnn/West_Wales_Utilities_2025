package uk.ac.cf.spring.client_project.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Integer userId;

    @NotBlank(message = "First name is required")
    private String firstName;

    private String lastName;


    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;


    public User() {
        this.userId = 0;
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.email = "";
    }
}