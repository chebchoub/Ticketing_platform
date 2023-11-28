package com.example.backend.Auth;

import com.example.backend.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:4200")
public class AuthenficationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request)
    {     Optional<User> existingUser = authenticationService.getByMail(request);

        if(existingUser.isEmpty()) {
            // L'utilisateur n'existe pas, vous pouvez l'ajouter à la base de données
            return ResponseEntity.ok(authenticationService.register(request));
        } else {
            // L'utilisateur existe déjà, renvoyez un code de statut 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody authenticationRequest request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

}
