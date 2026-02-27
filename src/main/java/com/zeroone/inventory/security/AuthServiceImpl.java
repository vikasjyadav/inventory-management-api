package com.zeroone.inventory.security;
import com.zeroone.inventory.dto.CreateManagerRequest;
import com.zeroone.inventory.exception.AuthenticationException;
import com.zeroone.inventory.exception.DuplicateResourceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();

        Optional<User> byUsername = userRepository.findByUsername(username);

        if (byUsername.isPresent()){
            User user = byUsername.get();
            boolean matches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

            if (matches){
                return user;
            }else {
                throw new AuthenticationException("Invalid username or password");
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }

    @Override
    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new DuplicateResourceException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STAFF);

        return userRepository.save(user);
    }

    @Override
    public User createManager(CreateManagerRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.MANAGER);
        return userRepository.save(user);
    }
}
