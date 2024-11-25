package uk.ac.cf.spring.client_project.location;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.Mockito.*;

@SpringBootTest
class LocationRepositoryTests {
    @Test
    void shouldSaveLocationToDatabase() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        LocationRepositoryImpl locationRepository = new LocationRepositoryImpl(jdbcTemplate);

        Location location = new Location();
        location.setName("Test Location");
        location.setAddressLine1("Test Address Line 1");
        location.setAddressLine2("Test Address Line 2");
        location.setCity("Test City");
        location.setPostcode("Test Postcode");
        location.setTypeId(1L);

        locationRepository.save(location);

        verify(jdbcTemplate).update("INSERT INTO location (name, address_line_1, address_line_2, city, postcode, type_id) VALUES (?, ?, ?, ?, ?, ?)",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId());
    }
}
