package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.CreditApplication;
import com.coopcredit.application.infrastructure.entities.CreditApplicationEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T14:36:33-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class CreditApplicationMapperImpl implements CreditApplicationMapper {

    @Autowired
    private AffiliateMapper affiliateMapper;
    @Autowired
    private RiskEvaluationMapper riskEvaluationMapper;

    @Override
    public CreditApplication toDomain(CreditApplicationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CreditApplication.CreditApplicationBuilder creditApplication = CreditApplication.builder();

        creditApplication.affiliate( affiliateMapper.toDomain( entity.getAffiliate() ) );
        creditApplication.riskEvaluation( riskEvaluationMapper.toDomain( entity.getRiskEvaluation() ) );
        creditApplication.id( entity.getId() );
        creditApplication.requestedAmount( entity.getRequestedAmount() );
        creditApplication.termMonths( entity.getTermMonths() );
        creditApplication.purpose( entity.getPurpose() );
        creditApplication.status( entity.getStatus() );
        creditApplication.applicationDate( entity.getApplicationDate() );
        creditApplication.evaluationDate( entity.getEvaluationDate() );
        creditApplication.approvalAmount( entity.getApprovalAmount() );
        creditApplication.monthlyQuota( entity.getMonthlyQuota() );
        creditApplication.debtToIncomeRatio( entity.getDebtToIncomeRatio() );

        return creditApplication.build();
    }

    @Override
    public CreditApplicationEntity toEntity(CreditApplication domain) {
        if ( domain == null ) {
            return null;
        }

        CreditApplicationEntity.CreditApplicationEntityBuilder creditApplicationEntity = CreditApplicationEntity.builder();

        creditApplicationEntity.affiliate( affiliateMapper.toEntity( domain.getAffiliate() ) );
        creditApplicationEntity.riskEvaluation( riskEvaluationMapper.toEntity( domain.getRiskEvaluation() ) );
        creditApplicationEntity.id( domain.getId() );
        creditApplicationEntity.requestedAmount( domain.getRequestedAmount() );
        creditApplicationEntity.termMonths( domain.getTermMonths() );
        creditApplicationEntity.purpose( domain.getPurpose() );
        creditApplicationEntity.status( domain.getStatus() );
        creditApplicationEntity.applicationDate( domain.getApplicationDate() );
        creditApplicationEntity.evaluationDate( domain.getEvaluationDate() );
        creditApplicationEntity.approvalAmount( domain.getApprovalAmount() );
        creditApplicationEntity.monthlyQuota( domain.getMonthlyQuota() );
        creditApplicationEntity.debtToIncomeRatio( domain.getDebtToIncomeRatio() );

        return creditApplicationEntity.build();
    }

    @Override
    public List<CreditApplication> toDomainList(List<CreditApplicationEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CreditApplication> list = new ArrayList<CreditApplication>( entities.size() );
        for ( CreditApplicationEntity creditApplicationEntity : entities ) {
            list.add( toDomain( creditApplicationEntity ) );
        }

        return list;
    }
}
