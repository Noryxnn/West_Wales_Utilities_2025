package uk.ac.cf.spring.client_project.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        User user = mock(User.class);
        userService.registerUser(user);
        verify(userRepository, times(1)).addUser(user);
    }

    @Test
    void testGetUserById() {
        User user = mock(User.class);
        when(userRepository.getUserById(1)).thenReturn(user);

        User result = userService.getUserById(1);
        assertEquals(user, result);
    }

    @Test
    void testGetAllUsers() {
        User user1 = mock(User.class);
        User user2 = mock(User.class);

        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.getAllUsers()).thenReturn(users);

        List<User> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    @Test
    void testFindUserByEmail() {
        User user1 = mock(User.class);
        when(user1.getEmail()).thenReturn("test1@example.com");

        User user2 = mock(User.class);
        when(user2.getEmail()).thenReturn("test2@example.com");

        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.getAllUsers()).thenReturn(users);

        Optional<User> user = userService.findUserByEmail("test1@example.com");
        assertTrue(user.isPresent());
        assertEquals("test1@example.com", user.get().getEmail());
    }
}
