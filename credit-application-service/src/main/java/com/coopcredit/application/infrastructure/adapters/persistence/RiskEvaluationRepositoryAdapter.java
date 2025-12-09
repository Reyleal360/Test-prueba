package com.coopcredit.application.infrastructure.adapters.persistence;

import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.domain.ports.out.RiskEvaluationRepositoryPort;
import com.coopcredit.application.infrastructure.entities.RiskEvaluationEntity;
import com.coopcredit.application.infrastructure.mappers.RiskEvaluationMapper;
import com.coopcredit.application.infrastructure.repositories.JpaRiskEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RiskEvaluationRepositoryAdapter implements RiskEvaluationRepositoryPort {

    private final JpaRiskEvaluationRepository repository;
    private final RiskEvaluationMapper mapper;

    @Override
    public RiskEvaluation save(RiskEvaluation riskEvaluation) {
        RiskEvaluationEntity entity = mapper.toEntity(riskEvaluation);
        RiskEvaluationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RiskEvaluation> findByCreditApplicationId(Long creditApplicationId) {
        return repository.findByCreditApplicationId(creditApplicationId).map(mapper::toDomain);
    }
}
