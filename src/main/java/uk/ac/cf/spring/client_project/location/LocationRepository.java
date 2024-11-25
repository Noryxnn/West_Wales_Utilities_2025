package uk.ac.cf.spring.client_project.location;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {
    private final JdbcTemplate jdbcTemplate;

    public  LocationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Location location) {
        jdbcTemplate.update("INSERT INTO location (name, address_line_1, address_line_2, city, postcode, type_id) VALUES (?, ?, ?, ?, ?, ?)",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId());
    }
}
