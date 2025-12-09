package com.coopcredit.application.infrastructure.adapters.persistence;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.domain.ports.out.AffiliateRepositoryPort;
import com.coopcredit.application.infrastructure.entities.AffiliateEntity;
import com.coopcredit.application.infrastructure.mappers.AffiliateMapper;
import com.coopcredit.application.infrastructure.repositories.JpaAffiliateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AffiliateRepositoryAdapter implements AffiliateRepositoryPort {

    private final JpaAffiliateRepository repository;
    private final AffiliateMapper mapper;

    @Override
    public Affiliate save(Affiliate affiliate) {
        AffiliateEntity entity = mapper.toEntity(affiliate);
        AffiliateEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Affiliate> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Affiliate> findByDocumentNumber(String documentNumber) {
        return repository.findByDocumentNumber(documentNumber).map(mapper::toDomain);
    }

    @Override
    public Optional<Affiliate> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public List<Affiliate> findAll() {
        return mapper.toDomainList(repository.findAll());
    }

    @Override
    public boolean existsByDocumentNumber(String documentNumber) {
        return repository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
