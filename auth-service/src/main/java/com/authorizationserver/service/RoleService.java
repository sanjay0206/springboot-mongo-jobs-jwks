package com.authorizationserver.service;

import com.authorizationserver.entity.Role;
import com.authorizationserver.exceptions.AuthAPIException;
import com.authorizationserver.exceptions.ResourceNotFoundException;
import com.authorizationserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(Role role) {
        Role newRole = roleRepository.findById(role.getId())
                .orElseThrow(() -> new AuthAPIException(HttpStatus.NOT_FOUND, "Role not found"));

        return roleRepository.save(newRole);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(String id) {
       return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }

    public void deleteRole(String id) {
       Role role = roleRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

       roleRepository.delete(role);
    }
}
