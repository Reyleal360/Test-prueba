package com.coopcredit.application.infrastructure.repositories;

import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.infrastructure.entities.CreditApplicationEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCreditApplicationRepository extends JpaRepository<CreditApplicationEntity, Long> {

    @EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
    List<CreditApplicationEntity> findAll();

    @EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
    Optional<CreditApplicationEntity> findById(Long id);

    @EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
    List<CreditApplicationEntity> findByAffiliateId(Long affiliateId);

    @EntityGraph(attributePaths = { "affiliate", "riskEvaluation" })
    List<CreditApplicationEntity> findByStatus(ApplicationStatus status);
}
