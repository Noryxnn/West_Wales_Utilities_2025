package uk.ac.cf.spring.client_project.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.cf.spring.client_project.user.Register;
import uk.ac.cf.spring.client_project.user.RegisterService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final String CLIENT_ID = "907822677520-21sch8i398ejsfkidnffmqtase9lnecq.apps.googleusercontent.com";

    private final RegisterService registerService;

    @Autowired
    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/google-login")
    public ResponseEntity<Map<String, String>> googleLogin(@RequestBody Map<String, String> payload) {
        String idToken = payload.get("idToken");
        Map<String, String> response = new HashMap<>();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken token = verifier.verify(idToken);

            if (token != null) {
                GoogleIdToken.Payload tokenPayload = token.getPayload();

                String email = tokenPayload.getEmail();
                Optional<Register> existingUser = registerService.findUserByEmail(email);

                if (existingUser.isPresent()) {
                    response.put("status", "success");
                    response.put("email", email);
                    response.put("firstName", existingUser.get().getFirstName());
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "error");
                    response.put("message", "User not registered. Please sign up first.");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("status", "error");
                response.put("message", "Invalid Google token");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Authentication failed");
            return ResponseEntity.badRequest().body(response);
        }
    }
}