package com.coopcredit.application.domain.ports.out;

import com.coopcredit.application.domain.model.RiskEvaluation;
import java.util.Optional;

public interface RiskEvaluationRepositoryPort {
    RiskEvaluation save(RiskEvaluation riskEvaluation);

    Optional<RiskEvaluation> findByCreditApplicationId(Long creditApplicationId);
}
