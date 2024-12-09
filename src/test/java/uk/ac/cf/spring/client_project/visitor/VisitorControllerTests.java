package uk.ac.cf.spring.client_project.visitor;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import uk.ac.cf.spring.client_project.qrcode.QRCodeGenerator;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VisitorControllerTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void shouldGetDashboard() throws Exception {
        mvc.perform(get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/visitor-dashboard"));
    }

    @Test
    void shouldGetCheckInWithQrCodeAttribute() throws Exception {

        try (MockedStatic<QRCodeGenerator> mockedQrCode = mockStatic(QRCodeGenerator.class)) {
            mockedQrCode.when(() -> QRCodeGenerator.getQRCode(300, 300))
                    .thenReturn("mockQrCode");  // Return a mock QR code string

            mvc.perform(get("/check-in"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(view().name("visitor/check-in"))
                    .andExpect(model().attributeExists("qrcode"));  // Assert the "qrcode" attribute exists
        }
    }

    //referenced from: https://stackoverflow.com/questions/21105403/mocking-static-methods-with-mockito


    @Test
    void shouldGetCheckInWithErrorIfQrCodeGenerationFails() throws Exception {

        mockStatic(QRCodeGenerator.class).when(() -> QRCodeGenerator.getQRCode(anyInt(), anyInt()))
                .thenThrow(new RuntimeException("QR generation failed"));

        mvc.perform(get("/check-in"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/check-in"))
                .andExpect(model().attributeExists("error"));
    }
}
