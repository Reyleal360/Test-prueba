package com.coopcredit.application.domain.ports.in.applications;

import com.coopcredit.application.domain.model.CreditApplication;

public interface GetCreditApplicationUseCase {
    CreditApplication getById(Long id);
}
