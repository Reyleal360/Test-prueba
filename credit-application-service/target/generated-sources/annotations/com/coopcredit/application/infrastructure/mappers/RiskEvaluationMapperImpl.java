package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.infrastructure.entities.RiskEvaluationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T15:29:51-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class RiskEvaluationMapperImpl implements RiskEvaluationMapper {

    @Override
    public RiskEvaluation toDomain(RiskEvaluationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RiskEvaluation.RiskEvaluationBuilder riskEvaluation = RiskEvaluation.builder();

        riskEvaluation.evaluationDate( entity.getEvaluationDate() );
        riskEvaluation.externalReference( entity.getExternalReference() );
        riskEvaluation.id( entity.getId() );
        riskEvaluation.recommendation( entity.getRecommendation() );
        riskEvaluation.riskLevel( entity.getRiskLevel() );
        riskEvaluation.riskScore( entity.getRiskScore() );

        return riskEvaluation.build();
    }

    @Override
    public RiskEvaluationEntity toEntity(RiskEvaluation domain) {
        if ( domain == null ) {
            return null;
        }

        RiskEvaluationEntity.RiskEvaluationEntityBuilder riskEvaluationEntity = RiskEvaluationEntity.builder();

        riskEvaluationEntity.evaluationDate( domain.getEvaluationDate() );
        riskEvaluationEntity.externalReference( domain.getExternalReference() );
        riskEvaluationEntity.id( domain.getId() );
        riskEvaluationEntity.recommendation( domain.getRecommendation() );
        riskEvaluationEntity.riskLevel( domain.getRiskLevel() );
        riskEvaluationEntity.riskScore( domain.getRiskScore() );

        return riskEvaluationEntity.build();
    }
}
