package com.authorizationserver.controller;

import com.authorizationserver.request.AuthRequest;
import com.authorizationserver.request.RefreshTokenRequest;
import com.authorizationserver.request.UserRequest;
import com.authorizationserver.service.AuthService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@Tag(name = "Authentication Controller", description = "APIs for user authentication and token management")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @Operation(summary = "Get JSON Web Key Set", description = "Retrieve public keys for verifying JWT tokens", hidden = true)
    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> getJwkSet() {
        log.info("Inside getJwkSet");
        return authService.getJwkSet();
    }

    @Operation(summary = "Register a new user", description = "Register a new user with username and password")
    @ApiResponse(responseCode = "200", description = "User successfully registered")
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        log.info("Registering new user: {}", userRequest);
        return ResponseEntity.ok(authService.registerUser(userRequest));
    }

    @Operation(summary = "Generate access token", description = "Authenticate user and generate access token")
    @ApiResponse(responseCode = "200", description = "Token successfully generated")
    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> generateToken(@RequestBody AuthRequest authRequest) throws JOSEException {
        log.info("Generating tokens for user: {}", authRequest.getUsername());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);

        Map<String, Object> accessToken = authService.getToken(authRequest.getUsername());

        return ResponseEntity.ok(accessToken);
    }

    @Operation(summary = "Refresh access token", description = "Generate new access token using refresh token")
    @ApiResponse(responseCode = "200", description = "Refresh token successfully generated")
    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String, Object>> generateRefreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
            throws ParseException, JOSEException {
        log.info("Refreshing token");

        Map<String, Object> refreshToken = authService.getRefreshToken(refreshTokenRequest.getRefreshToken());

        return ResponseEntity.ok(refreshToken);
    }
}
