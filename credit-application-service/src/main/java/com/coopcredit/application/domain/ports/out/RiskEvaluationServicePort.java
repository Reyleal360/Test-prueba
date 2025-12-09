package com.coopcredit.application.domain.ports.out;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.model.RiskEvaluation;
import java.math.BigDecimal;

public interface RiskEvaluationServicePort {
    RiskEvaluation evaluateRisk(String documentNumber, BigDecimal requestedAmount, BigDecimal monthlySalary);
}
