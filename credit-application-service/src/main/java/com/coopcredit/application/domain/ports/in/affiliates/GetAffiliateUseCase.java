package com.coopcredit.application.domain.ports.in.affiliates;

import com.coopcredit.application.domain.model.Affiliate;

public interface GetAffiliateUseCase {
    Affiliate getById(Long id);

    Affiliate getByDocumentNumber(String documentNumber);
}
