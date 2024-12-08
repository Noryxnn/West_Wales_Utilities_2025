package uk.ac.cf.spring.client_project.user;

public class UserLogin {
    private Long userId;
    private String email;
    private String password;

    public UserLogin(Long userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
