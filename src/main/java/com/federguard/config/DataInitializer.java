package com.federguard.config;

import com.federguard.model.User;
import com.federguard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Créer l'administrateur s'il n'existe pas
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass"));
            admin.setRoles(Set.of("ROLE_ADMIN"));
            userRepository.save(admin);
        }

        // Créer les clients s'ils n'existent pas
        for (int i = 1; i <= 5; i++) {
            String username = "client" + i;
            if (userRepository.findByUsername(username).isEmpty()) {
                User client = new User();
                client.setUsername(username);
                client.setPassword(passwordEncoder.encode("clientpass" + i));
                client.setRoles(Set.of("ROLE_CLIENT"));
                userRepository.save(client);
            }
        }
    }
}

