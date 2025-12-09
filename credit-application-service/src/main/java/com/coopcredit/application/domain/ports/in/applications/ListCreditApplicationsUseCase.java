package com.coopcredit.application.domain.ports.in.applications;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import java.util.List;

public interface ListCreditApplicationsUseCase {
    List<CreditApplication> listAll();

    List<CreditApplication> listByAffiliate(Long affiliateId);

    List<CreditApplication> listByStatus(ApplicationStatus status);
}
