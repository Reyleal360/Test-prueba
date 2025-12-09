package com.coopcredit.application.domain.ports.in.applications;

import com.coopcredit.application.domain.model.CreditApplication;

public interface EvaluateCreditApplicationUseCase {
    CreditApplication evaluate(Long creditApplicationId);
}
