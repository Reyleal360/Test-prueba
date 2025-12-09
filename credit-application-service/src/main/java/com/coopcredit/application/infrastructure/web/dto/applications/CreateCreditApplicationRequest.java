package com.coopcredit.application.infrastructure.web.dto.applications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditApplicationRequest {
    private Long affiliateId;
    private BigDecimal requestedAmount;
    private Integer termMonths;
    private String purpose;
}
