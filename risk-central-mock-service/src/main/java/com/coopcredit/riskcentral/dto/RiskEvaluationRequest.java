package com.coopcredit.riskcentral.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskEvaluationRequest {
    private String documentNumber;
    private BigDecimal requestedAmount;
    private BigDecimal monthlySalary;
}
