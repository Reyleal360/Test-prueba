package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.User;
import com.coopcredit.application.infrastructure.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T15:29:51-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.createdAt( entity.getCreatedAt() );
        user.email( entity.getEmail() );
        user.enabled( entity.getEnabled() );
        user.id( entity.getId() );
        user.password( entity.getPassword() );
        user.role( entity.getRole() );
        user.username( entity.getUsername() );

        return user.build();
    }

    @Override
    public UserEntity toEntity(User domain) {
        if ( domain == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.createdAt( domain.getCreatedAt() );
        userEntity.email( domain.getEmail() );
        userEntity.enabled( domain.getEnabled() );
        userEntity.id( domain.getId() );
        userEntity.password( domain.getPassword() );
        userEntity.role( domain.getRole() );
        userEntity.username( domain.getUsername() );

        return userEntity.build();
    }
}
