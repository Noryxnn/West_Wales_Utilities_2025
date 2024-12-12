package uk.ac.cf.spring.client_project.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
    @WithMockUser(username="john@doe.com", roles = {"ADMIN"})
    void shouldGetLocationNames() throws Exception {
        List<Location> locations = List.of(
                new Location(1L, "Test Location 1", "Address 1", "Address 2", "City", "AB12 3CD", 1L),
                new Location(2L, "Test Location 2", "Address 1", "Address 2", "City", "A34B 5CD", 2L)
        );
        when(locationService.getLocations()).thenReturn(locations);

        MvcResult result = mvc
                .perform(get("/admin/locations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("<td><a href=\"/admin/locations/1\">Test Location 1</a></td>"));
        assertTrue(content.contains("<td><a href=\"/admin/locations/2\">Test Location 2</a></td>"));
    }

    @Test
    @WithMockUser(username="john@doe.com", roles = {"ADMIN"})
    void shouldGetLocationDetailsById() throws Exception {
        Location location = new Location(1L, "Test Location 1", "Address 1", "Address 2", "City", "AB12 3CD", 1L);
        LocationType locationType = new LocationType(1L, "Other");

        when(locationService.getLocationById(1L)).thenReturn(location);
        when(locationService.getLocationTypeById(location.getTypeId())).thenReturn(locationType);

        MvcResult result = mvc
                .perform(get("/admin/locations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertTrue(content.contains("Test Location 1"));
        assertTrue(content.contains("Address 1"));
        assertTrue(content.contains("Address 2"));
        assertTrue(content.contains("City"));
        assertTrue(content.contains("AB12 3CD"));
        assertTrue(content.contains("Other"));
    }

    @Test
    @WithMockUser(username="john@doe.com", roles = {"ADMIN"})
    void shouldSuccessfullyAddLocation() throws Exception {

        mvc.perform(post("/admin/locations/add")
                        .with(csrf())
                        .param("id", "1")
                        .param("name", "Test Location")
                        .param("addressLine1", "123 Street")
                        .param("addressLine2", "Suite 4")
                        .param("city", "CityName")
                        .param("postcode", "AB12 3CD")
                        .param("typeId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/locations"));

        verify(locationService).save(any());
    }

    // Tests adapted from Wiliam's Location tests, thank you Wiliam
    @Test
    @WithMockUser(username="john@doe.com", roles = {"ADMIN"})
    void shouldSuccessfullyEditLocation() throws Exception {
        mvc.perform(post("/admin/locations/edit/1")
                        .with(csrf())
                        .param("id", "1")
                        .param("name", "Cardiff Office")
                        .param("addressLine1", "123 Street")
                        .param("addressLine2", "")
                        .param("city", "CityName")
                        .param("postcode", "CF11 2AB")
                        .param("typeId", "1")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/locations"));

        verify(locationService).save(any());
    }

    @Test
    @WithMockUser(username="john@doe.com", roles={"ADMIN"})
    void shouldSuccessfullyDeleteLocation() throws Exception {
        Location mockLocation = new Location(1L, "Test Location", "123 Street", "Suite 4", "CityName", "AB12 3CD", 1L);
        when(locationService.getLocationById(1L)).thenReturn(mockLocation);
        mvc.perform(post("/admin/locations/delete/confirm/1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/locations"));
        verify(locationService).delete(mockLocation);
    }

}
