package com.coopcredit.application.application.usecases.applications;

import com.coopcredit.application.domain.exception.NotFoundException;
import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.ports.in.applications.GetCreditApplicationUseCase;
import com.coopcredit.application.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCreditApplicationUseCaseImpl implements GetCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;

    @Override
    public CreditApplication getById(Long id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Credit application not found with ID: " + id));
    }
}
