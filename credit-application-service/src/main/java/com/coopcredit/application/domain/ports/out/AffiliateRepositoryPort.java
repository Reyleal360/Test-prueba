package com.coopcredit.application.domain.ports.out;

import com.coopcredit.application.domain.model.Affiliate;
import java.util.Optional;
import java.util.List;

public interface AffiliateRepositoryPort {
    Affiliate save(Affiliate affiliate);

    Optional<Affiliate> findById(Long id);

    Optional<Affiliate> findByDocumentNumber(String documentNumber);

    Optional<Affiliate> findByEmail(String email);

    List<Affiliate> findAll();

    boolean existsByDocumentNumber(String documentNumber);

    boolean existsByEmail(String email);
}
