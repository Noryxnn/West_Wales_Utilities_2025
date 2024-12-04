package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cf.spring.client_project.security.QREncryptionUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class QRScannerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldDecryptValidQrCode() throws Exception {
        String encryptedData = QREncryptionUtils.encrypt("testData");

        mockMvc.perform(post("/staff/scan").
                        contentType(MediaType.TEXT_PLAIN).
                        content(encryptedData))
                .andExpect(status().isOk())
                .andExpect(content().string("QR code processed successfully!"));
    }

    @Test
    void shouldReturnBadRequestForInvalidQrCode() throws Exception {
        mockMvc.perform(post("/staff/scan").
                        contentType(MediaType.TEXT_PLAIN).
                        content("invalidData"))
                .andExpect(status().isBadRequest());
    }
}
