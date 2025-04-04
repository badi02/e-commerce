package com.devs.ecommerce.config;

import com.devs.ecommerce.model.User;
import com.devs.ecommerce.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if an admin user already exists
            if (userRepository.findByEmail("admin@ecommerce.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@ecommerce.com");
                admin.setPassword(passwordEncoder.encode("Admin@Password@123"));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);

                System.out.println("Admin user initialized successfully.");
            } else {
                System.out.println("Admin user already exists.");
            }
        };
    }
}
