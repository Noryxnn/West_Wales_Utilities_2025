package uk.ac.cf.spring.client_project.staff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
}
