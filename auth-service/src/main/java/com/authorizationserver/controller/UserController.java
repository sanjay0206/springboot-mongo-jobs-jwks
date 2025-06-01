package com.authorizationserver.controller;

import com.authorizationserver.entity.UserEntity;
import com.authorizationserver.request.UserRequest;
import com.authorizationserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/users")
@Tag(name = "User Controller", description = "APIs for managing users (Admin access required for most operations)")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user by ID", description = "Retrieve user details by user ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user")
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user", description = "Create a new user account (Admin only)")
    @ApiResponse(responseCode = "200", description = "User successfully created")
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserRequest userRequest) {
        UserEntity savedUser = userService.createUser(userRequest);
        return ResponseEntity.ok(savedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users (Admin only)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users")
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID", description = "Delete a user by their ID (Admin only)")
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
