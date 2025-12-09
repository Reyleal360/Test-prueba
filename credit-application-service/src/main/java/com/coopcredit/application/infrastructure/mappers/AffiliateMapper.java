package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.infrastructure.entities.AffiliateEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AffiliateMapper {
    Affiliate toDomain(AffiliateEntity entity);

    AffiliateEntity toEntity(Affiliate domain);

    List<Affiliate> toDomainList(List<AffiliateEntity> entities);
}
