package uk.ac.cf.spring.client_project.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class LocationRepositoryImpl implements LocationRepository {


    private final JdbcTemplate jdbcTemplate;
    private RowMapper<Location> locationRowMapper;
    private RowMapper<LocationType> locationTypeRowMapper;

    @Autowired
    public  LocationRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        setLocationRowMapper();
        setLocationTypeRowMapper();
    }


    private void setLocationRowMapper() {
        locationRowMapper = (rs, i) -> new Location(
                rs.getLong("location_id"),
                rs.getString("name"),
                rs.getString("address_line_1"),
                rs.getString("address_line_2"),
                rs.getString("city"),
                rs.getString("postcode"),
                rs.getObject("type_id") != null ? rs.getLong("type_id") : null
        );
    }

    private void setLocationTypeRowMapper() {
        locationTypeRowMapper = (rs, i) -> new LocationType(
                rs.getLong("type_id"),
                rs.getString("name")
        );
    }

    public List<Location> getLocations() {
        return jdbcTemplate.query("SELECT * FROM locations", locationRowMapper);
    }

    public Location getLocationById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM locations WHERE location_id = ?", locationRowMapper, id);
    }

    public void save(Location location) {
        jdbcTemplate.update("INSERT INTO locations (name, address_line_1, address_line_2, city, postcode, type_id) VALUES (?, ?, ?, ?, ?, ?)",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId());
    }

    public List<LocationType> getLocationTypes() {
        return jdbcTemplate.query("SELECT * FROM location_types", locationTypeRowMapper);
    }

    public LocationType getLocationTypeById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM location_types WHERE type_id = ?", locationTypeRowMapper, id);
    }
}
