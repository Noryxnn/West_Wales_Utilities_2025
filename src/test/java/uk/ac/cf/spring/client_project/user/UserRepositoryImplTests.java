package uk.ac.cf.spring.client_project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserRepositoryImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleUser = new User(1, "John", "Doe", "password123", "john.doe@example.com", true);
    }

    @Test
    void testGetAllUsers() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(sampleUser));

        List<User> users = userRepository.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(sampleUser.getEmail(), users.get(0).getEmail());
    }

    @Test
    void testGetUserById() {
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(sampleUser);

        User user = userRepository.getUserById(1);

        assertNotNull(user);
        assertEquals(sampleUser.getEmail(), user.getEmail());
    }

    @Test
    void testSaveUser() {
        when(jdbcTemplate.update(
                eq("INSERT INTO users (first_name, last_name, password, email, enabled) VALUES (?, ?, ?, ?, ?)"),
                eq("John"), eq("Doe"), eq("password123"), eq("john.doe@example.com"), eq(true)
        )).thenReturn(1);

        assertDoesNotThrow(() -> userRepository.save(sampleUser));
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any());
    }

    @Test
    void testDeleteUserById() {
        when(jdbcTemplate.update(eq("DELETE FROM users WHERE user_id = ?"), eq(1))).thenReturn(1);

        assertDoesNotThrow(() -> userRepository.deleteUserById(1));
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt());
    }

    @Test
    void testAddUser() {
        when(jdbcTemplate.update(
                eq("INSERT INTO users(first_name, last_name, password, email, enabled) VALUES (?,?,?,?, ?)"),
                eq("John"), eq("Doe"), eq("password123"), eq("john.doe@example.com"), eq(true)
        )).thenReturn(1);

        assertDoesNotThrow(() -> userRepository.addUser(sampleUser));
        verify(jdbcTemplate, times(1)).update(anyString(), any(), any(), any(), any(), any());
    }

    @Test
    void testFindByEmail() {
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM users WHERE email = ?"),
                any(RowMapper.class), eq("john.doe@example.com")
        )).thenReturn(sampleUser);

        Optional<User> user = userRepository.findByEmail("john.doe@example.com");

        assertTrue(user.isPresent());
        assertEquals(sampleUser.getEmail(), user.get().getEmail());
    }

    @Test
    void testGetJdbcTemplate() {
        JdbcTemplate jdbcTemplateInstance = userRepository.getJdbcTemplate();

        assertNotNull(jdbcTemplateInstance);
        assertEquals(jdbcTemplate, jdbcTemplateInstance);
    }
}
