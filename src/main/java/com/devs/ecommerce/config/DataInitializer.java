package com.devs.ecommerce.config;

import com.devs.ecommerce.model.User;
import com.devs.ecommerce.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if an admin user already exists
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);

                System.out.println("Admin user initialized successfully.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
