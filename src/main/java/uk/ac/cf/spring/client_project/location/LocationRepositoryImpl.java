package uk.ac.cf.spring.client_project.location;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationRepositoryImpl implements LocationRepository {
    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Location> locationRowMapper;

    public  LocationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setLocationRowMapper();
    }

    private void setLocationRowMapper() {
        locationRowMapper = (rs, i) -> new Location(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("address_line_1"),
                rs.getString("address_line_2"),
                rs.getString("city"),
                rs.getString("postcode"),
                rs.getLong("type_id")
        );
    }

    public List<Location> getLocations() {
        return jdbcTemplate.query("SELECT * FROM location", locationRowMapper);
    }

    public void save(Location location) {
        jdbcTemplate.update("INSERT INTO location (name, address_line_1, address_line_2, city, postcode, type_id) VALUES (?, ?, ?, ?, ?, ?)",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId());
    }
}
