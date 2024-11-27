package uk.ac.cf.spring.client_project.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.List;

@SpringBootTest
class LocationRepositoryTests {

    @MockBean
    JdbcTemplate jdbcTemplate;

    @Autowired
    LocationRepository locationRepository;
    @Test
    void shouldSaveLocationToDatabase() {
        Location location = new Location();
        location.setName("Test Location");
        location.setAddressLine1("Test Address Line 1");
        location.setAddressLine2("Test Address Line 2");
        location.setCity("Test City");
        location.setPostcode("AB12 3CD");
        location.setTypeId(1L);

        locationRepository.save(location);

        verify(jdbcTemplate).update("INSERT INTO locations (name, address_line_1, address_line_2, city, postcode, type_id) VALUES (?, ?, ?, ?, ?, ?)",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId());
    }

    @Test
    void shouldGetAllLocations() {
        List<Location> mockLocations = List.of(
                new Location(1L, "Location 1", "Address 1", "Address 2", "City", "AB12 3CD", 1L),
                new Location(2L, "Location 2", "Address 1", "Address 2", "City", "A34B 5CD", 2L)
        );

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mockLocations);

        List<Location> locations = locationRepository.getLocations();

        assertEquals(2, locations.size());
    }
}
