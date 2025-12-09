package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.infrastructure.entities.RiskEvaluationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RiskEvaluationMapper {
    @Mapping(target = "creditApplication", ignore = true) // Avoid cycle
    RiskEvaluation toDomain(RiskEvaluationEntity entity);

    @Mapping(target = "creditApplication", ignore = true)
    RiskEvaluationEntity toEntity(RiskEvaluation domain);
}
