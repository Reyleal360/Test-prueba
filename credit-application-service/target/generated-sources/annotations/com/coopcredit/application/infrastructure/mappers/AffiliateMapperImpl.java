package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.Affiliate;
import com.coopcredit.application.infrastructure.entities.AffiliateEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T15:29:51-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class AffiliateMapperImpl implements AffiliateMapper {

    @Override
    public Affiliate toDomain(AffiliateEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Affiliate.AffiliateBuilder affiliate = Affiliate.builder();

        affiliate.createdAt( entity.getCreatedAt() );
        affiliate.documentNumber( entity.getDocumentNumber() );
        affiliate.email( entity.getEmail() );
        affiliate.firstName( entity.getFirstName() );
        affiliate.id( entity.getId() );
        affiliate.lastName( entity.getLastName() );
        affiliate.monthlySalary( entity.getMonthlySalary() );
        affiliate.phoneNumber( entity.getPhoneNumber() );
        affiliate.seniority( entity.getSeniority() );
        affiliate.status( entity.getStatus() );
        affiliate.updatedAt( entity.getUpdatedAt() );

        return affiliate.build();
    }

    @Override
    public AffiliateEntity toEntity(Affiliate domain) {
        if ( domain == null ) {
            return null;
        }

        AffiliateEntity.AffiliateEntityBuilder affiliateEntity = AffiliateEntity.builder();

        affiliateEntity.createdAt( domain.getCreatedAt() );
        affiliateEntity.documentNumber( domain.getDocumentNumber() );
        affiliateEntity.email( domain.getEmail() );
        affiliateEntity.firstName( domain.getFirstName() );
        affiliateEntity.id( domain.getId() );
        affiliateEntity.lastName( domain.getLastName() );
        affiliateEntity.monthlySalary( domain.getMonthlySalary() );
        affiliateEntity.phoneNumber( domain.getPhoneNumber() );
        affiliateEntity.seniority( domain.getSeniority() );
        affiliateEntity.status( domain.getStatus() );
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
