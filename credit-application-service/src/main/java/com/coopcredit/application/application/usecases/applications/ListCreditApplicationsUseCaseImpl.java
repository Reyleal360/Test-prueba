package com.coopcredit.application.application.usecases.applications;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.ports.in.applications.ListCreditApplicationsUseCase;
import com.coopcredit.application.domain.ports.out.CreditApplicationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCreditApplicationsUseCaseImpl implements ListCreditApplicationsUseCase {

    private final CreditApplicationRepositoryPort repositoryPort;

    @Override
    public List<CreditApplication> listAll() {
        return repositoryPort.findAll();
    }

    @Override
    public List<CreditApplication> listByAffiliate(Long affiliateId) {
        return repositoryPort.findByAffiliateId(affiliateId);
    }

    @Override
    public List<CreditApplication> listByStatus(ApplicationStatus status) {
        return repositoryPort.findByStatus(status);
    }
}
