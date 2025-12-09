package com.coopcredit.application.infrastructure.repositories;

import com.coopcredit.application.infrastructure.entities.AffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAffiliateRepository extends JpaRepository<AffiliateEntity, Long> {
    Optional<AffiliateEntity> findByDocumentNumber(String documentNumber);

    Optional<AffiliateEntity> findByEmail(String email);

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);
}
