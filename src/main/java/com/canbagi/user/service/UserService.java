package com.canbagi.user.service;

import com.canbagi.user.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO getUserById(UUID userId);
    UserResponseDTO getUserByEmail(String email);
    boolean existsByEmail(String email);
    List<UserResponseDTO> getAllUsers();
    void deactivateUser(UUID userId);
}
