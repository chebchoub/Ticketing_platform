package com.example.backend.testAuth;

import com.example.backend.Auth.AuthenticationResponse;
import com.example.backend.Auth.AuthenticationService;
import com.example.backend.Auth.RegisterRequest;
import com.example.backend.Auth.authenticationRequest;
import com.example.backend.Config.JwtService;
import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john@example.com", "password");
        User user = new User("John", "Doe", "john@example.com", "encodedPassword", Role.USER);

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("mockedJwtToken");

        // Act
        AuthenticationResponse result = authenticationService.register(registerRequest);

        // Assert
        assertNotNull(result);
        assertEquals("mockedJwtToken", result.getToken());
    }
    @Test
    public void testAuthenticate_Success() {
        // Given
        String email = "john@example.com";
        String password = "password";
        User user = new User("John", "Doe", email, "encodedPassword", Role.USER);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        // When
        AuthenticationResponse response = authenticationService.authenticate(new authenticationRequest(email, password));

        // Then
        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
    }
    @Test
    public void testAuthenticate_UserNotFound() {
        // Given
        String email = "john@example.com";
        String password = "password";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // When
        assertThrows(IllegalArgumentException.class, () -> authenticationService.authenticate(new authenticationRequest(email, password)));
    }

}
