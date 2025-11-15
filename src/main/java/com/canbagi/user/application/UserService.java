package com.canbagi.user.application;

import com.canbagi.user.application.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO getUserById(UUID userId);
    UserResponseDTO getUserByEmail(String email);
    boolean existsByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    void deactivateUser(UUID userId);
}
