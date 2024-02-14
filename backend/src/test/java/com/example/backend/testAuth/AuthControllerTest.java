package com.example.backend.testAuth;

import com.example.backend.Auth.*;
import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenficationController authenficationController;

    @Test
    public void testRegister_Success() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", "password", Role.USER);
        AuthenticationResponse mockedResponse = AuthenticationResponse.builder().token("mockedJwtToken").build();

        when(authenticationService.getByMail(any(RegisterRequest.class))).thenReturn(Optional.empty());
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(mockedResponse);

        // Act
        ResponseEntity<AuthenticationResponse> responseEntity = authenficationController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("mockedJwtToken", responseEntity.getBody().getToken());
    }

    @Test
    public void testRegister_UserAlreadyExists() {
        // Arrange
        RegisterRequest registerRequest = new RegisterRequest("John", "Doe", "john.doe@example.com", "password",Role.USER);

        when(authenticationService.getByMail(any(RegisterRequest.class))).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<AuthenticationResponse> responseEntity = authenficationController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void testAuthentificate_Success(){
        //Arange
        authenticationRequest authenticationRequest = new authenticationRequest("Aymen","12345678");
        AuthenticationResponse mockedResponse = AuthenticationResponse.builder().token("mockedJwtToken").build();

        when(authenticationService.authenticate(any(authenticationRequest.class))).thenReturn(mockedResponse);

        //Act
        ResponseEntity<AuthenticationResponse> responseEntity = authenficationController.authentificate(authenticationRequest);

        //Assert
        assertEquals(HttpStatus.OK ,responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("mockedJwtToken",responseEntity.getBody().getToken());
    }

    @Test
    public void testAuthenticate_Failure() {
        // Arrange
        authenticationRequest authRequest = new authenticationRequest("john.doe@example.com", "password");

        // Mock authentication failure by throwing an exception
        when(authenticationService.authenticate(any(authenticationRequest.class)))
                .thenReturn(null);

        // Act
        ResponseEntity<AuthenticationResponse> responseEntity = authenficationController.authentificate(authRequest);

        // Assert
        assertNull(responseEntity.getBody());
    }
}