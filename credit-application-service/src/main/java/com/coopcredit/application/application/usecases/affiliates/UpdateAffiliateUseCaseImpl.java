package com.coopcredit.application.application.usecases.affiliates;

import com.coopcredit.application.domain.exception.NotFoundException;
import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.ports.in.affiliates.UpdateAffiliateUseCase;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateAffiliateUseCaseImpl implements UpdateAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;

    @Override
    @Transactional
    public Affiliate update(Long id, Affiliate affiliate) {
        Affiliate existingAffiliate = affiliateRepositoryPort.findById(id)
                .orElseThrow(() -> new NotFoundException("Affiliate not found with ID: " + id));

        existingAffiliate.setFirstName(affiliate.getFirstName());
        existingAffiliate.setLastName(affiliate.getLastName());
        existingAffiliate.setEmail(affiliate.getEmail());
        existingAffiliate.setPhoneNumber(affiliate.getPhoneNumber());
        existingAffiliate.setMonthlySalary(affiliate.getMonthlySalary());
        existingAffiliate.setSeniority(affiliate.getSeniority());
        existingAffiliate.setUpdatedAt(LocalDateTime.now());

        return affiliateRepositoryPort.save(existingAffiliate);
    }
}
