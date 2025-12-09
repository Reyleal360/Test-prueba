package com.coopcredit.application.domain.ports.in.affiliates;

import com.coopcredit.application.domain.model.Affiliate;

public interface UpdateAffiliateUseCase {
    Affiliate update(Long id, Affiliate affiliate);
}
