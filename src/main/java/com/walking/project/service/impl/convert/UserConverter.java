package com.walking.project.service.impl.convert;

import com.walking.project.dataobject.dto.UserDTO;
import com.walking.project.dataobject.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    @Mappings(@Mapping(source="name",target="nameForTest"))
    UserDTO do2dto(User user);
}
