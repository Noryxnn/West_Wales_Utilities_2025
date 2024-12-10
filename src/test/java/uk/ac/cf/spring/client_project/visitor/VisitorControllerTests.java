package uk.ac.cf.spring.client_project.visitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
    @WithMockUser(username = "john@doe.com", roles = {"VISITOR"})
    void shouldGetDashboard() throws Exception {
        mvc.perform(get("/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/visitor-dashboard"));
    }

    @Test
    @WithMockUser(username = "john@doe.com", roles = {"VISITOR"})
    void shouldGetCheckInWithQrCodeAttribute() throws Exception {
        mvc.perform(get("/check-in"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("visitor/check-in"))
                .andExpect(model().attributeExists("qrcode"));
    }

    @Test
    @WithMockUser(username = "john@doe.com", roles = {"VISITOR"})
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
