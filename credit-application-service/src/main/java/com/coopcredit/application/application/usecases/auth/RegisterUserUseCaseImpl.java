package com.coopcredit.application.application.usecases.auth;

import com.coopcredit.application.domain.exception.BusinessRuleException;
import com.coopcredit.application.domain.model.User;
import com.coopcredit.application.domain.ports.in.auth.RegisterUserUseCase;
import com.coopcredit.application.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User register(User user) {
        if (userRepositoryPort.existsByUsername(user.getUsername())) {
            throw new BusinessRuleException("Username already exists");
        }
        if (userRepositoryPort.existsByEmail(user.getEmail())) {
            throw new BusinessRuleException("Email already exists");
        }
        return userRepositoryPort.save(user);
    }
}
