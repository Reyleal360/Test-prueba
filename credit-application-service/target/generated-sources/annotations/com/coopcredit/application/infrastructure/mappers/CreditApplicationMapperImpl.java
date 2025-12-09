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
    date = "2025-12-09T15:29:51-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
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
        creditApplication.applicationDate( entity.getApplicationDate() );
        creditApplication.approvalAmount( entity.getApprovalAmount() );
        creditApplication.debtToIncomeRatio( entity.getDebtToIncomeRatio() );
        creditApplication.evaluationDate( entity.getEvaluationDate() );
        creditApplication.id( entity.getId() );
        creditApplication.monthlyQuota( entity.getMonthlyQuota() );
        creditApplication.purpose( entity.getPurpose() );
        creditApplication.requestedAmount( entity.getRequestedAmount() );
        creditApplication.status( entity.getStatus() );
        creditApplication.termMonths( entity.getTermMonths() );

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
        creditApplicationEntity.applicationDate( domain.getApplicationDate() );
        creditApplicationEntity.approvalAmount( domain.getApprovalAmount() );
        creditApplicationEntity.debtToIncomeRatio( domain.getDebtToIncomeRatio() );
        creditApplicationEntity.evaluationDate( domain.getEvaluationDate() );
        creditApplicationEntity.id( domain.getId() );
        creditApplicationEntity.monthlyQuota( domain.getMonthlyQuota() );
        creditApplicationEntity.purpose( domain.getPurpose() );
        creditApplicationEntity.requestedAmount( domain.getRequestedAmount() );
        creditApplicationEntity.status( domain.getStatus() );
        creditApplicationEntity.termMonths( domain.getTermMonths() );

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
