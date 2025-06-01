package com.authorizationserver.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users") // MongoDB collection name
public class UserEntity {

    @Id
    private String id;  // MongoDB uses String (ObjectId) as the ID type by default
    private String username;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private LocalDateTime createdAt;

    // Reference to multiple roles
    @DBRef
    private List<Role> roles;

    public UserEntity(String username, String email, String password, List<Role> roles, LocalDateTime createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.createdAt = createdAt;
    }
}
