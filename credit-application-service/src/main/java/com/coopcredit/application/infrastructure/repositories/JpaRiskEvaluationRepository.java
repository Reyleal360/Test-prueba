package com.coopcredit.application.infrastructure.repositories;

import com.coopcredit.application.infrastructure.entities.RiskEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaRiskEvaluationRepository extends JpaRepository<RiskEvaluationEntity, Long> {
    Optional<RiskEvaluationEntity> findByCreditApplicationId(Long creditApplicationId);
}
