package com.coopcredit.application.domain.ports.in.auth;

import com.coopcredit.application.domain.model.User;

public interface AuthenticateUserUseCase {
    User authenticate(String username, String password);
}
