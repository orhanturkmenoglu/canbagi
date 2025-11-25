package com.canbagi.user.application.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.user.application.dto.response.UserResponseDTO;
import com.canbagi.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserResponseDTO, User> {
}
