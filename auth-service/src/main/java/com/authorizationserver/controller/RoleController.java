package com.authorizationserver.controller;

import com.authorizationserver.entity.Role;
import com.authorizationserver.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/roles")
@Tag(name = "Role Controller", description = "APIs for managing user roles (Admin access required)")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new role", description = "Create a new user role (Admin only)")
    @ApiResponse(responseCode = "200", description = "Role successfully created")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role savedRole = roleService.createRole(role);
        return ResponseEntity.ok(savedRole);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all roles", description = "Retrieve a list of all roles (Admin only)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of roles")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get role by ID", description = "Retrieve a role by its ID (Admin only)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved role")
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable String id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete role by ID", description = "Delete a role by its ID (Admin only)")
    @ApiResponse(responseCode = "204", description = "Role successfully deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
