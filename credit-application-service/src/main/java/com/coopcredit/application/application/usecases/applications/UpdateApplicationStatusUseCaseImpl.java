package com.coopcredit.application.application.usecases.applications;

import com.coopcredit.application.domain.exception.NotFoundException;
import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.ports.in.applications.UpdateApplicationStatusUseCase;
import com.coopcredit.application.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateApplicationStatusUseCaseImpl implements UpdateApplicationStatusUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;

    @Override
    @Transactional
    public CreditApplication updateStatus(Long id, ApplicationStatus status) {
        CreditApplication application = repositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Credit application not found with ID: " + id));

        application.setStatus(status);
        if (status == ApplicationStatus.APPROVED && application.getApprovalAmount() == null) {
            application.setApprovalAmount(application.getRequestedAmount());
        }
        application.setEvaluationDate(LocalDateTime.now());

        return repositoryPort.save(application);
    }
}
