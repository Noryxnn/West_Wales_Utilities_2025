package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    @WithMockUser(username="john@doe.com", roles={"STAFF", "ADMIN"})
    void shouldGetStaffDashboard() throws Exception {
        mockMvc.perform(get("/staff/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("staff/staff-dashboard"));
    }

    @Test
    @WithMockUser(username = "john@doe.com", roles={"STAFF", "ADMIN"})
    void shouldGetLocationsWithDashboard() throws Exception {
        mockMvc.perform(get("/staff/dashboard"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("locations"));
    }

    @Test
    @WithMockUser(username = "john@doe.com", roles = {"STAFF", "ADMIN"})
    void shouldDenyAccessToScannerWithoutLocation() throws Exception {
        mockMvc.perform(post("/staff/scan")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "john@doe.com", roles={"STAFF", "ADMIN"})
    void shouldAllowAccessToScannerWithValidLocation() throws Exception {
        mockMvc.perform(post("/staff/scan")
                        .param("locationId", "1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("staff/qr-scanner"));
    }

}
