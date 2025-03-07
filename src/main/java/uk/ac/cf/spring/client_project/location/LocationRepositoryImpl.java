package uk.ac.cf.spring.client_project.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        if (location.isNew()) {
            insert(location);
        } else {
            update(location);
        }
    }

    private void insert(Location location) {
        jdbcTemplate.update("INSERT INTO locations (name, address_line_1, address_line_2, city, postcode, type_id) VALUES (?, ?, ?, ?, ?, ?)",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId());
    }

    private void update(Location location) {
        jdbcTemplate.update("UPDATE locations SET name = ?, address_line_1 = ?, address_line_2 = ?, city = ?, postcode = ?, type_id = ? WHERE location_id = ?",
                location.getName(), location.getAddressLine1(), location.getAddressLine2(), location.getCity(), location.getPostcode(), location.getTypeId(), location.getId());
    }

    public List<LocationType> getLocationTypes() {
        return jdbcTemplate.query("SELECT * FROM location_types", locationTypeRowMapper);
    }

    public LocationType getLocationTypeById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM location_types WHERE type_id = ?", locationTypeRowMapper, id);
    }

    public void delete(Location location) {
        String deleteSql = "UPDATE locations SET deleted = true WHERE location_id = ?";
        jdbcTemplate.update(deleteSql, location.getId());
    }

    public void archive(Location location) {
        String sql = "INSERT INTO locations_archive (location_id, name, address_line_1, address_line_2, city, postcode, type_id, deleted_at) values (?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                location.getId(),
                location.getName(),
                location.getAddressLine1(),
                location.getAddressLine2(),
                location.getCity(),
                location.getPostcode(),
                location.getTypeId(),
                Timestamp.valueOf(LocalDateTime.now())
        );
    }

    public void deletePermanently(Location location) {
        String deleteSql = "DELETE FROM locations WHERE location_id = ?";
        jdbcTemplate.update(deleteSql, location.getId());
    }

    public List<Location> findDeletedLocations() {
        String sql = "SELECT * FROM locations WHERE deleted = true";
        return jdbcTemplate.query(sql, locationRowMapper);
    }

}
