package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QRScannerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username="john@doe.com", roles = {"STAFF"})
    void shouldReturnBadRequestForInvalidQrCode() throws Exception {
        mockMvc.perform(post("/scan").
                        contentType(MediaType.TEXT_PLAIN).
                        content("invalidData")
                        .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}
