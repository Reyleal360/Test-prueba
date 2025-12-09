package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.User;
import com.coopcredit.application.infrastructure.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-09T14:36:33-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.9 (Ubuntu)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( entity.getId() );
        user.username( entity.getUsername() );
        user.password( entity.getPassword() );
        user.email( entity.getEmail() );
        user.role( entity.getRole() );
        user.enabled( entity.getEnabled() );
        user.createdAt( entity.getCreatedAt() );

        return user.build();
    }

    @Override
    public UserEntity toEntity(User domain) {
        if ( domain == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( domain.getId() );
        userEntity.username( domain.getUsername() );
        userEntity.password( domain.getPassword() );
        userEntity.email( domain.getEmail() );
        userEntity.role( domain.getRole() );
        userEntity.enabled( domain.getEnabled() );
        userEntity.createdAt( domain.getCreatedAt() );

        return userEntity.build();
    }
}
