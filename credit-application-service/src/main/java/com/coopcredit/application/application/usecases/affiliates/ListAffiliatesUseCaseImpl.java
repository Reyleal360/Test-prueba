package com.coopcredit.application.application.usecases.affiliates;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.ports.in.affiliates.ListAffiliatesUseCase;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAffiliatesUseCaseImpl implements ListAffiliatesUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;

    @Override
    public List<Affiliate> listAll() {
        return affiliateRepositoryPort.findAll();
    }
}
