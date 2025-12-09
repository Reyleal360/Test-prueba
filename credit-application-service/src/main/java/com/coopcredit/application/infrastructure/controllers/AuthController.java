package com.coopcredit.application.infrastructure.controllers;

import com.coopcredit.application.domain.model.User;
import com.coopcredit.application.domain.model.enums.UserRole;
import com.coopcredit.application.domain.ports.in.auth.AuthenticateUserUseCase;
import com.coopcredit.application.domain.ports.in.auth.RegisterUserUseCase;
import com.coopcredit.application.infrastructure.services.JwtService;
import com.coopcredit.application.infrastructure.web.dto.auth.AuthResponse;
import com.coopcredit.application.infrastructure.web.dto.auth.LoginRequest;
import com.coopcredit.application.infrastructure.web.dto.auth.RegisterRequest;
import com.coopcredit.application.infrastructure.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final AuthenticateUserUseCase authenticateUserUseCase; // Can be simplified with auth manager logic in
                                                                   // controller but let's stick to use case or direct
                                                                   // logic
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    // We'll implement simplified logic here for brevity as auth logic often sits
    // between usecase and infra
    // Ideally authentication is an Application Service

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(UserRole.ROLE_AFILIADO) // Default role
                .enabled(true)
                .build();

        registerUserUseCase.register(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Typically AuthenticationManager.authenticate() is used here
        // We will call our UseCase or simpler, use the component
        // Let's assume authenticateUserUseCase does the check
        User user = authenticateUserUseCase.authenticate(request.getUsername(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }
}
