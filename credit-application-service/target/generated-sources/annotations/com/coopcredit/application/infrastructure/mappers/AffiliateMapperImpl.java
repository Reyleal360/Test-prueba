package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.infrastructure.entities.AffiliateEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T14:36:33-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class AffiliateMapperImpl implements AffiliateMapper {

    @Override
    public Affiliate toDomain(AffiliateEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Affiliate.AffiliateBuilder affiliate = Affiliate.builder();

        affiliate.id( entity.getId() );
        affiliate.documentNumber( entity.getDocumentNumber() );
        affiliate.firstName( entity.getFirstName() );
        affiliate.lastName( entity.getLastName() );
        affiliate.email( entity.getEmail() );
        affiliate.phoneNumber( entity.getPhoneNumber() );
        affiliate.monthlySalary( entity.getMonthlySalary() );
        affiliate.seniority( entity.getSeniority() );
        affiliate.status( entity.getStatus() );
        affiliate.createdAt( entity.getCreatedAt() );
        affiliate.updatedAt( entity.getUpdatedAt() );

        return affiliate.build();
    }

    @Override
    public AffiliateEntity toEntity(Affiliate domain) {
        if ( domain == null ) {
            return null;
        }

        AffiliateEntity.AffiliateEntityBuilder affiliateEntity = AffiliateEntity.builder();

        affiliateEntity.id( domain.getId() );
        affiliateEntity.documentNumber( domain.getDocumentNumber() );
        affiliateEntity.firstName( domain.getFirstName() );
        affiliateEntity.lastName( domain.getLastName() );
        affiliateEntity.email( domain.getEmail() );
        affiliateEntity.phoneNumber( domain.getPhoneNumber() );
        affiliateEntity.monthlySalary( domain.getMonthlySalary() );
        affiliateEntity.seniority( domain.getSeniority() );
        affiliateEntity.status( domain.getStatus() );
        affiliateEntity.createdAt( domain.getCreatedAt() );
        affiliateEntity.updatedAt( domain.getUpdatedAt() );

        return affiliateEntity.build();
    }

    @Override
    public List<Affiliate> toDomainList(List<AffiliateEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<Affiliate> list = new ArrayList<Affiliate>( entities.size() );
        for ( AffiliateEntity affiliateEntity : entities ) {
            list.add( toDomain( affiliateEntity ) );
        }

        return list;
    }
}
