package uk.ac.cf.spring.client_project.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.client_project.user.User;
import uk.ac.cf.spring.client_project.user.UserService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticatorController {
    private static final String CLIENT_ID = "907822677520-21sch8i398ejsfkidnffmqtase9lnecq.apps.googleusercontent.com";

    private final UserService userService;

    @Autowired
    public AuthenticatorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/verify")
    public Map<String, Object> verifyToken(@RequestBody TokenRequest tokenRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idToken = verifier.verify(tokenRequest.getIdToken());

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String firstName = (String) payload.get("given_name");
                String lastName = (String) payload.get("family_name");

                // Register or retrieve user
                User user = userService.registerGoogleUser(email, firstName, lastName);

                response.put("status", "success");
                response.put("message", "Successfully verified and registered user");
                response.put("userId", user.getUserId());
                response.put("email", user.getEmail());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());

                return response;
            } else {
                response.put("status", "error");
                response.put("message", "Invalid ID token.");
                return response;
            }
        } catch (GeneralSecurityException | IOException e) {
            response.put("status", "error");
            response.put("message", "Token verification failed: " + e.getMessage());
            return response;
        }
    }

    public static class TokenRequest {
        private String idToken;

        public String getIdToken() {
            return idToken;
        }

        public void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}