package com.canbagi.user.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.user.dto.response.UserResponseDTO;
import com.canbagi.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserResponseDTO, User> {
}
