package com.coopcredit.application.domain.model;

import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplication {
    private Long id;
    private Affiliate affiliate;
    private BigDecimal requestedAmount;
    private Integer termMonths;
    private String purpose;
    private ApplicationStatus status;
    private LocalDateTime applicationDate;
    private LocalDateTime evaluationDate;
    private BigDecimal approvalAmount;
    private BigDecimal monthlyQuota;
    private BigDecimal debtToIncomeRatio;
    private RiskEvaluation riskEvaluation;
}
