package com.coopcredit.application.domain.ports.in.affiliates;

import com.coopcredit.application.domain.model.Affiliate;

public interface RegisterAffiliateUseCase {
    Affiliate register(Affiliate affiliate);
}
