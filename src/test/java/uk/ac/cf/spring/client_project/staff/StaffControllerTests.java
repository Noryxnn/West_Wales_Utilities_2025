package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StaffControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetStaffDashboard() throws Exception {
        mockMvc.perform(get("/staff/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("staff/staff-dashboard"));
    }

    @Test
    void shouldGetLocationsWithDashboard() throws Exception {
        mockMvc.perform(get("/staff/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("locations"));
    }

    @Test
    void shouldDenyAccessToScannerWithoutLocation() throws Exception {
        mockMvc.perform(post("/staff/scan"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAllowAccessToScannerWithValidLocation() throws Exception {
        mockMvc.perform(post("/staff/scan")
                        .param("locationId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("staff/qr-scanner"));
    }

}
