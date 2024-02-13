package com.example.backend.Auth;

import com.example.backend.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin("http://localhost:4200")
public class AuthenficationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, errors));
        }
        Optional<User> existingUser = authenticationService.getByMail(request);
        if (existingUser.isEmpty()) {
            return ResponseEntity.ok(authenticationService.register(request));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody authenticationRequest request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

}
