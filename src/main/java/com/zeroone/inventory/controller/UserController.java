package com.zeroone.inventory.controller;

import com.zeroone.inventory.dto.CreateManagerRequest;
import com.zeroone.inventory.security.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create-manager")
    public ResponseEntity<String> createManager(
            @Valid @RequestBody CreateManagerRequest createManagerRequest) {

        authService.createManager(createManagerRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Manager created successfully");
    }

}