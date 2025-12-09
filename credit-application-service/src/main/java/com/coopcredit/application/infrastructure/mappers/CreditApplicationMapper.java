package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.infrastructure.entities.CreditApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { AffiliateMapper.class, RiskEvaluationMapper.class })
public interface CreditApplicationMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "affiliate", source = "affiliate")
    @Mapping(target = "requestedAmount", source = "requestedAmount")
    @Mapping(target = "termMonths", source = "termMonths")
    @Mapping(target = "purpose", source = "purpose")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "applicationDate", source = "applicationDate")
    @Mapping(target = "evaluationDate", source = "evaluationDate")
    @Mapping(target = "approvalAmount", source = "approvalAmount")
    @Mapping(target = "monthlyQuota", source = "monthlyQuota")
    @Mapping(target = "debtToIncomeRatio", source = "debtToIncomeRatio")
    @Mapping(target = "riskEvaluation", source = "riskEvaluation")
    CreditApplication toDomain(CreditApplicationEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "affiliate", source = "affiliate")
    @Mapping(target = "requestedAmount", source = "requestedAmount")
    @Mapping(target = "termMonths", source = "termMonths")
    @Mapping(target = "purpose", source = "purpose")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "applicationDate", source = "applicationDate")
    @Mapping(target = "evaluationDate", source = "evaluationDate")
    @Mapping(target = "approvalAmount", source = "approvalAmount")
    @Mapping(target = "monthlyQuota", source = "monthlyQuota")
    @Mapping(target = "debtToIncomeRatio", source = "debtToIncomeRatio")
    @Mapping(target = "riskEvaluation", source = "riskEvaluation")
    CreditApplicationEntity toEntity(CreditApplication domain);

    List<CreditApplication> toDomainList(List<CreditApplicationEntity> entities);
}
