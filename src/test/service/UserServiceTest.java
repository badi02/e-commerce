package com.devs.ecommerce.service;

import com.devs.ecommerce.dto.ChangePasswordRequest;
import com.devs.ecommerce.exception.ResourceNotFoundException;
import com.devs.ecommerce.model.User;
import com.devs.ecommerce.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private ChangePasswordRequest changePasswordRequest;

    @BeforeEach
    void setUp() {
        user = new User(1L, "test@example.com", "encodedPass", User.Role.ADMIN);

        changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setCurrentPassword("oldPass");
        changePasswordRequest.setNewPassword("newPass");
    }

    @Test
    void getUserByEmail_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User result = userService.getUserByEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserByEmail("test@example.com"));
    }

    @Test
    void changePassword_Success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "encodedPass")).thenReturn(true);
        when(passwordEncoder.encode("newPass")).thenReturn("newEncodedPass");
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.changePassword("test@example.com", changePasswordRequest);

        verify(userRepository).save(user);
        assertEquals("newEncodedPass", user.getPassword());
    }

    @Test
    void changePassword_IncorrectCurrentPassword() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("oldPass", "encodedPass")).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> userService.changePassword("test@example.com", changePasswordRequest));
    }

    @Test
    void getUserById_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }
}