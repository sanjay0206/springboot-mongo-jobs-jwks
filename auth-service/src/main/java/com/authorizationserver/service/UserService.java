package com.authorizationserver.service;

import com.authorizationserver.entity.Role;
import com.authorizationserver.entity.UserEntity;
import com.authorizationserver.exceptions.AuthAPIException;
import com.authorizationserver.exceptions.ResourceNotFoundException;
import com.authorizationserver.repository.RoleRepository;
import com.authorizationserver.repository.UserRepository;
import com.authorizationserver.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        List<Role> validatedRoles = roleRepository.findByNameIn(userRequest.getRoles());
        if (validatedRoles.isEmpty()) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "No valid roles found for the user");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(validatedRoles);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public void deleteUser(String id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(userEntity);
    }
}
