package uk.ac.cf.spring.client_project.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private LocationService locationService;


    @Test
    void shouldGetLocationNames() throws Exception {

        MvcResult result = mvc
                .perform(get("/admin/locations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<td>Test Location 1</td>"));
        assertTrue(content.contains("<td>Test Location 2</td>"));
    }

    @Test
    void shouldSuccessfullyAddLocation() throws Exception {

        mvc.perform(post("/admin/locations/add-location")
                .param("id", "1")
                .param("name", "Valid Name")
                .param("addressLine1", "123 Street")
                .param("addressLine2", "Suite 4")
                .param("city", "CityName")
                .param("postcode", "12345")
                .param("typeId", "1")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/admin/locations"));

        verify(locationService).addLocation(any());
    }
}
