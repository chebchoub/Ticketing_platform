package com.example.backend.Auth;

import com.example.backend.Config.JwtService;
import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(request.getNom(),request.getPrenom(), request.getEmail(), passwordEncoder.encode(request.getPassword()),Role.USER);

            userRepository.save(user);
            var jwtToken=jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();


    }

    public Optional<User> getByMail(RegisterRequest request)
    {
        return userRepository.findByEmail(request.getEmail());
    }
    public AuthenticationResponse authenticate(authenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
          var user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
