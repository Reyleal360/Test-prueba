package com.coopcredit.application.application.usecases.affiliates;

import com.coopcredit.application.domain.exception.BusinessRuleException;
import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.model.enums.AffiliateStatus;
import com.coopcredit.application.domain.ports.in.affiliates.RegisterAffiliateUseCase;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterAffiliateUseCaseImpl implements RegisterAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;

    @Override
    @Transactional
    public Affiliate register(Affiliate affiliate) {
        if (affiliateRepositoryPort.existsByDocumentNumber(affiliate.getDocumentNumber())) {
            throw new BusinessRuleException("Affiliate with this document number already exists");
        }
        if (affiliateRepositoryPort.existsByEmail(affiliate.getEmail())) {
            throw new BusinessRuleException("Affiliate with this email already exists");
        }
        if (affiliate.getMonthlySalary().signum() <= 0) {
            throw new BusinessRuleException("Monthly salary must be greater than zero");
        }

        affiliate.setStatus(AffiliateStatus.ACTIVE);
        affiliate.setCreatedAt(LocalDateTime.now());
        affiliate.setUpdatedAt(LocalDateTime.now());

        return affiliateRepositoryPort.save(affiliate);
    }
}
