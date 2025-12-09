package com.coopcredit.application.infrastructure.adapters.persistence;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.domain.model.enums.ApplicationStatus;
import com.coopcredit.application.domain.ports.out.CreditApplicationRepositoryPort;
import com.coopcredit.application.infrastructure.entities.CreditApplicationEntity;
import com.coopcredit.application.infrastructure.mappers.CreditApplicationMapper;
import com.coopcredit.application.infrastructure.repositories.JpaCreditApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CreditApplicationRepositoryAdapter implements CreditApplicationRepositoryPort {

    private final JpaCreditApplicationRepository repository;
    private final CreditApplicationMapper mapper;

    @Override
    public CreditApplication save(CreditApplication creditApplication) {
        CreditApplicationEntity entity = mapper.toEntity(creditApplication);
        CreditApplicationEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CreditApplication> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<CreditApplication> findAll() {
        return mapper.toDomainList(repository.findAll());
    }

    @Override
    public List<CreditApplication> findByAffiliateId(Long affiliateId) {
        return mapper.toDomainList(repository.findByAffiliateId(affiliateId));
    }

    @Override
    public List<CreditApplication> findByStatus(ApplicationStatus status) {
        return mapper.toDomainList(repository.findByStatus(status));
    }
}
