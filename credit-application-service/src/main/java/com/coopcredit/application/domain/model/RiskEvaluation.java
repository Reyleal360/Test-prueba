package com.coopcredit.application.domain.model;

import com.coopcredit.application.domain.model.enums.RiskLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluation {
    private Long id;
    private CreditApplication creditApplication;
    private Integer riskScore;
    private RiskLevel riskLevel;
    private LocalDateTime evaluationDate;
    private String externalReference;
    private String recommendation;
}
