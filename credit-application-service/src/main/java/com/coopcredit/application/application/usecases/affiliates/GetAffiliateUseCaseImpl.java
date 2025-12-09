package com.coopcredit.application.application.usecases.affiliates;

import com.coopcredit.application.domain.exception.NotFoundException;
import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.ports.in.affiliates.GetAffiliateUseCase;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAffiliateUseCaseImpl implements GetAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;

    @Override
    public Affiliate getById(Long id) {
        return affiliateRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Affiliate not found with ID: " + id));
    }

    @Override
    public Affiliate getByDocumentNumber(String documentNumber) {
        return affiliateRepositoryPort.findByDocumentNumber(documentNumber)
                .orElseThrow(() -> new NotFoundException("Affiliate not found with document: " + documentNumber));
    }
}
