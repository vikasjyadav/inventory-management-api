package com.zeroone.inventory.security;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {

        User user = authService.login(loginRequest);

        String token = jwtUtil.generateToken(user.getUsername());

        LoginResponse response =
                new LoginResponse(token, user.getRole());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        User registeredUser = authService.register(registerRequest);

        return new ResponseEntity<>(new RegisterResponse("Registration completed" ,registeredUser.getRole()), HttpStatus.CREATED);
    }

}