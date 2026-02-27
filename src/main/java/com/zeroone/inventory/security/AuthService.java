package com.zeroone.inventory.security;

import com.zeroone.inventory.dto.CreateManagerRequest;

public interface AuthService {
    User login(LoginRequest loginRequest);

    User register(RegisterRequest request);

    User createManager(CreateManagerRequest request);
}
