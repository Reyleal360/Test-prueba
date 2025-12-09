package com.coopcredit.application.domain.ports.out;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepositoryPort {
    CreditApplication save(CreditApplication creditApplication);

    Optional<CreditApplication> findById(Long id);

    List<CreditApplication> findAll();

    List<CreditApplication> findByAffiliateId(Long affiliateId);

    List<CreditApplication> findByStatus(ApplicationStatus status);
}
