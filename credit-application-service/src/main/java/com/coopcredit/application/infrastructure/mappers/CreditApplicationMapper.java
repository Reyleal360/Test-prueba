package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.infrastructure.entities.CreditApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AffiliateMapper.class, RiskEvaluationMapper.class })
public interface CreditApplicationMapper {

    @Mapping(target = "affiliate", source = "affiliate")
    @Mapping(target = "riskEvaluation", source = "riskEvaluation")
    CreditApplication toDomain(CreditApplicationEntity entity);

    @Mapping(target = "affiliate", source = "affiliate")
    @Mapping(target = "riskEvaluation", source = "riskEvaluation")
    CreditApplicationEntity toEntity(CreditApplication domain);

    List<CreditApplication> toDomainList(List<CreditApplicationEntity> entities);
}
