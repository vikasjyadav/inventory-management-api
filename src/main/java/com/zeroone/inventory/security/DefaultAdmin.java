package com.zeroone.inventory.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.zeroone.inventory.security.Role.ADMIN;

@Component


public class DefaultAdmin implements CommandLineRunner {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DefaultAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findByUsername("admin").isEmpty()){
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@gmil.com");
            user.setPassword(passwordEncoder.encode("Admin@123"));
            user.setRole(ADMIN);
            userRepository.save(user);
        }
    }
}
