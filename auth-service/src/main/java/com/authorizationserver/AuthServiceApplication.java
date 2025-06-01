package com.authorizationserver;

import com.authorizationserver.entity.Role;
import com.authorizationserver.entity.UserEntity;
import com.authorizationserver.repository.RoleRepository;
import com.authorizationserver.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {
			roleRepository.deleteAll();
			userRepository.deleteAll();

			PasswordEncoder encoder = new BCryptPasswordEncoder();
			Role developerRole = new Role(null, "DEVELOPER", "Developer Role");
			Role employerRole = new Role(null, "EMPLOYER", "Employer Role");
			Role adminRole = new Role(null, "ADMIN", "Admin Role");
			roleRepository.saveAll(Arrays.asList(developerRole, employerRole, adminRole));

			List<UserEntity> users = new ArrayList<>();
			users.add(new UserEntity("john_doe", "john@gmail.com", encoder.encode("john123"), List.of(developerRole), LocalDateTime.now()));
			users.add(new UserEntity("alice_smith", "alice@gmail.com", encoder.encode("alice123"), List.of(developerRole), LocalDateTime.now()));
			users.add(new UserEntity("sarah", "sarah@gmail.com", encoder.encode("sarah123"), List.of(employerRole), LocalDateTime.now()));
			users.add(new UserEntity("peter", "peter@gmail.com", encoder.encode("peter123"), List.of(adminRole), LocalDateTime.now()));

			userRepository.saveAll(users);
		};
	}
}
