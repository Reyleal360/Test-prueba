package com.coopcredit.application.application.usecases.applications;

import com.coopcredit.application.domain.exception.BusinessRuleException;
import com.coopcredit.application.domain.exception.NotFoundException;
import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.domain.model.enums.AffiliateStatus;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.model.enums.RiskLevel;
import com.coopcredit.application.domain.ports.in.applications.RegisterCreditApplicationUseCase;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import com.coopcredit.application.domain.ports.out.CreditApplicationRepositoryPort;
import com.coopcredit.application.domain.ports.out.RiskEvaluationRepositoryPort;
import com.coopcredit.application.domain.ports.out.RiskEvaluationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegisterCreditApplicationUseCaseImpl implements RegisterCreditApplicationUseCase {

    private final AffiliateRepositoryPort affiliateRepositoryPort;
    private final CreditApplicationRepositoryPort applicationRepositoryPort;
    private final RiskEvaluationRepositoryPort riskEvaluationRepositoryPort;
    private final RiskEvaluationServicePort riskEvaluationServicePort;

    @Override
    @Transactional
    public CreditApplication register(Long affiliateId, CreditApplication creditApplication) {
        Affiliate affiliate = affiliateRepositoryPort.findById(affiliateId)
                .orElseThrow(() -> new NotFoundException("Affiliate not found"));

        validateBusinessRules(affiliate, creditApplication);

        creditApplication.setAffiliate(affiliate);
        creditApplication.setStatus(ApplicationStatus.PENDING);
        creditApplication.setApplicationDate(LocalDateTime.now());

        calculateFinancials(creditApplication, affiliate);

        CreditApplication savedApplication = applicationRepositoryPort.save(creditApplication);

        evaluateRisk(savedApplication, affiliate);

        return savedApplication;
    }

    private void validateBusinessRules(Affiliate affiliate, CreditApplication application) {
        if (affiliate.getStatus() != AffiliateStatus.ACTIVE) {
            throw new BusinessRuleException("Affiliate is not active");
        }
        if (affiliate.getSeniority() < 6) {
            throw new BusinessRuleException("Affiliate seniority must be at least 6 months");
        }
        if (application.getRequestedAmount().compareTo(affiliate.getMonthlySalary().multiply(BigDecimal.TEN)) > 0) {
            throw new BusinessRuleException("Requested amount exceeds 10 times the monthly salary");
        }
        if (application.getTermMonths() < 6 || application.getTermMonths() > 60) {
            throw new BusinessRuleException("Term must be between 6 and 60 months");
        }
    }

    private void calculateFinancials(CreditApplication application, Affiliate affiliate) {
        BigDecimal interestRate = new BigDecimal("0.015");
        BigDecimal onePlusRate = BigDecimal.ONE.add(interestRate);
        BigDecimal amount = application.getRequestedAmount();
        BigDecimal term = new BigDecimal(application.getTermMonths());

        // Simple calculation for quota: (Amount * (1 + rate)) / term
        BigDecimal monthlyQuota = amount.multiply(onePlusRate).divide(term, MathContext.DECIMAL128);
        application.setMonthlyQuota(monthlyQuota);

        BigDecimal debtToIncomeRatio = monthlyQuota.divide(affiliate.getMonthlySalary(), MathContext.DECIMAL128)
                .multiply(new BigDecimal(100));
        application.setDebtToIncomeRatio(debtToIncomeRatio);

        if (debtToIncomeRatio.compareTo(new BigDecimal(40)) > 0) {
            throw new BusinessRuleException("Debt to income ratio exceeds 40%");
        }
    }

    private void evaluateRisk(CreditApplication application, Affiliate affiliate) {
        RiskEvaluation riskEvaluation = riskEvaluationServicePort.evaluateRisk(
                affiliate.getDocumentNumber(),
                application.getRequestedAmount(),
                affiliate.getMonthlySalary());

        riskEvaluation.setCreditApplication(application);
        riskEvaluationRepositoryPort.save(riskEvaluation);
        application.setRiskEvaluation(riskEvaluation);

        applyRiskRules(application, riskEvaluation);
        applicationRepositoryPort.save(application);
    }

    private void applyRiskRules(CreditApplication application, RiskEvaluation evaluation) {
        int score = evaluation.getRiskScore();
        if (score < 300) {
            application.setStatus(ApplicationStatus.REJECTED);
        } else if (score <= 500) {
            application.setStatus(ApplicationStatus.IN_REVIEW);
        } else if (score <= 700) {
            application.setStatus(ApplicationStatus.APPROVED); // Approved with conditions
            application.setApprovalAmount(application.getRequestedAmount().multiply(new BigDecimal("0.8")));
        } else {
            application.setStatus(ApplicationStatus.APPROVED);
            application.setApprovalAmount(application.getRequestedAmount());
        }
        application.setEvaluationDate(LocalDateTime.now());
    }
}
