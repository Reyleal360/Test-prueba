package com.coopcredit.riskcentral.dto;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskEvaluationResponse {
    private String documentNumber;
    private Integer riskScore;
    private String riskLevel;
    private LocalDateTime evaluationDate;
    private String externalReference;
    private String recommendation;
}
