package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.RiskEvaluation;
import com.coopcredit.application.infrastructure.entities.RiskEvaluationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T14:36:33-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class RiskEvaluationMapperImpl implements RiskEvaluationMapper {

    @Override
    public RiskEvaluation toDomain(RiskEvaluationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        RiskEvaluation.RiskEvaluationBuilder riskEvaluation = RiskEvaluation.builder();

        riskEvaluation.id( entity.getId() );
        riskEvaluation.riskScore( entity.getRiskScore() );
        riskEvaluation.riskLevel( entity.getRiskLevel() );
        riskEvaluation.evaluationDate( entity.getEvaluationDate() );
        riskEvaluation.externalReference( entity.getExternalReference() );
        riskEvaluation.recommendation( entity.getRecommendation() );

        return riskEvaluation.build();
    }

    @Override
    public RiskEvaluationEntity toEntity(RiskEvaluation domain) {
        if ( domain == null ) {
            return null;
        }

        RiskEvaluationEntity.RiskEvaluationEntityBuilder riskEvaluationEntity = RiskEvaluationEntity.builder();

        riskEvaluationEntity.id( domain.getId() );
        riskEvaluationEntity.riskScore( domain.getRiskScore() );
        riskEvaluationEntity.riskLevel( domain.getRiskLevel() );
        riskEvaluationEntity.evaluationDate( domain.getEvaluationDate() );
        riskEvaluationEntity.externalReference( domain.getExternalReference() );
        riskEvaluationEntity.recommendation( domain.getRecommendation() );

        return riskEvaluationEntity.build();
    }
}
