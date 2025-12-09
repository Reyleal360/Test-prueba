package com.coopcredit.application.infrastructure.mappers;

import com.coopcredit.application.domain.model.User;
import com.coopcredit.application.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User domain);
}
