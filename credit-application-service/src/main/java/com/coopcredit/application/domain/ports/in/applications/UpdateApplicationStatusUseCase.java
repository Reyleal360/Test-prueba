package com.coopcredit.application.domain.ports.in.applications;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;

public interface UpdateApplicationStatusUseCase {
    CreditApplication updateStatus(Long id, ApplicationStatus status);
}
