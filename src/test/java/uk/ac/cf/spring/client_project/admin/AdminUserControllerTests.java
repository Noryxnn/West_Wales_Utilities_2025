package uk.ac.cf.spring.client_project.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.ac.cf.spring.client_project.request.admin.AdminUserController;
import uk.ac.cf.spring.client_project.user.User;
import uk.ac.cf.spring.client_project.user.UserService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminUserControllerTests {

    @InjectMocks
    private AdminUserController adminUserController;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowAddUserForm() {
        // Act
        String viewName = adminUserController.showAddUserForm(model);

        // Assert
        verify(model).addAttribute(eq("user"), any(User.class));
        assertEquals("request/admin/addingusers", viewName);
    }

    @Test
    void testAddUser_Success_AdminRole() {
        // Arrange
        User user = new User();
        user.setPassword("password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        String viewName = adminUserController.addUser(user, bindingResult, "ADMIN");

        // Assert
        verify(userService).registerUser(user);
        verify(userService).assignRole(user.getEmail(), "ADMIN");
        assertEquals("redirect:/admin/dashboard", viewName);
    }

    @Test
    void testAddUser_Success_StaffRole() {
        // Arrange
        User user = new User();
        user.setPassword("password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        String viewName = adminUserController.addUser(user, bindingResult, "STAFF");

        // Assert
        verify(userService).registerUser(user);
        verify(userService).assignRole(user.getEmail(), "STAFF");
        assertEquals("redirect:/admin/dashboard", viewName);
    }

    @Test
    void testAddUser_Failure_BindingErrors() {
        // Arrange
        User user = new User();
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String viewName = adminUserController.addUser(user, bindingResult, "ADMIN");

        // Assert
        verify(userService, never()).registerUser(any(User.class));
        verify(userService, never()).assignRole(anyString(), anyString());
        assertEquals("request/admin/addingusers", viewName);
    }

    @Test
    void testAddUser_NullRole_DefaultsToStaff() {
        // Arrange
        User user = new User();
        user.setPassword("password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        String viewName = adminUserController.addUser(user, bindingResult, null);

        // Assert
        verify(userService).registerUser(user);
        verify(userService).assignRole(user.getEmail(), "STAFF");
        assertEquals("redirect:/admin/dashboard", viewName);
    }

    @Test
    void testAddUser_EmptyRole_DefaultsToStaff() {
        // Arrange
        User user = new User();
        user.setPassword("password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        String viewName = adminUserController.addUser(user, bindingResult, "");

        // Assert
        verify(userService).registerUser(user);
        verify(userService).assignRole(user.getEmail(), "STAFF");
        assertEquals("redirect:/admin/dashboard", viewName);
    }

    @Test
    void testAddUser_InvalidRole_DefaultsToStaff() {
        // Arrange
        User user = new User();
        user.setPassword("password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        String viewName = adminUserController.addUser(user, bindingResult, "INVALID_ROLE");

        // Assert
        verify(userService).registerUser(user);
        verify(userService).assignRole(user.getEmail(), "STAFF");
        assertEquals("redirect:/admin/dashboard", viewName);
    }

    @Test
    void testPasswordEncoding() {
        // Arrange
        User user = new User();
        user.setPassword("plainTextPassword");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        adminUserController.addUser(user, bindingResult, "ADMIN");

        // Assert
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testAddUser_DisabledUserInitially() {
        // Arrange
        User user = new User();
        user.setPassword("password123");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        adminUserController.addUser(user, bindingResult, "ADMIN");

        // Assert
        assertTrue(user.getEnabled());
    }
}