package uk.ac.cf.spring.client_project.qrcode;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.servlet.http.HttpSession;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class QRScanControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QRScanService qrScanService;
    @Mock
    private HttpSession session;

    @Test
    @WithMockUser(username="john@doe.com", roles = {"STAFF"})
    void testHandleQRCodeScanApprovedVisit () throws Exception {
        String qrData = "mockQRData";
        Long locationId = 1L;

        when(session.getAttribute("locationId")).thenReturn(locationId);
        when(qrScanService.scanQRCode(qrData, locationId)).thenReturn(ResponseEntity.ok("success"));

        mockMvc.perform(post("/scan")
                        .content(qrData)
                        .contentType(MediaType.TEXT_PLAIN)
                        .sessionAttr("locationId", locationId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        verify(qrScanService, times(1)).scanQRCode(qrData, locationId);
    }

    @Test
    @WithMockUser(username="john@doe.com", roles = {"STAFF"})
    void testHandleQRCodeScanDeniedVisit () throws Exception {
        String qrData = "mockQRData";
        Long locationId = 2L;

        when(session.getAttribute("locationId")).thenReturn(locationId);
        when(qrScanService.scanQRCode(qrData, locationId)).thenReturn(ResponseEntity.ok("denied"));

        mockMvc.perform(post("/scan")
                        .content(qrData)
                        .contentType(MediaType.TEXT_PLAIN)
                        .sessionAttr("locationId", locationId)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("denied"));

        verify(qrScanService, times(1)).scanQRCode(qrData, locationId);
    }
}
