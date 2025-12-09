package com.coopcredit.application.infrastructure.adapters.persistence;

import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.domain.ports.out.RiskEvaluationRepositoryPort;
import com.coopcredit.application.infrastructure.entities.CreditApplicationEntity;
import com.coopcredit.application.infrastructure.entities.RiskEvaluationEntity;
import com.coopcredit.application.infrastructure.mappers.RiskEvaluationMapper;
import com.coopcredit.application.infrastructure.repositories.JpaCreditApplicationRepository;
import com.coopcredit.application.infrastructure.repositories.JpaRiskEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RiskEvaluationRepositoryAdapter implements RiskEvaluationRepositoryPort {

    private final JpaRiskEvaluationRepository repository;
    private final JpaCreditApplicationRepository creditApplicationRepository;
    private final RiskEvaluationMapper mapper;

    @Override
    public RiskEvaluation save(RiskEvaluation riskEvaluation) {
        RiskEvaluationEntity entity = mapper.toEntity(riskEvaluation);
        
        // Set the credit application entity from the domain object
        if (riskEvaluation.getCreditApplication() != null && riskEvaluation.getCreditApplication().getId() != null) {
            CreditApplicationEntity creditAppEntity = creditApplicationRepository
                    .findById(riskEvaluation.getCreditApplication().getId())
                    .orElseThrow(() -> new RuntimeException("Credit application not found with ID: " + riskEvaluation.getCreditApplication().getId()));
            entity.setCreditApplication(creditAppEntity);
        }
        
        RiskEvaluationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RiskEvaluation> findByCreditApplicationId(Long creditApplicationId) {
        return repository.findByCreditApplicationId(creditApplicationId).map(mapper::toDomain);
    }
}
