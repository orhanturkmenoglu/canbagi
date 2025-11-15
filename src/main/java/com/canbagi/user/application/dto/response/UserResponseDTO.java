package com.canbagi.user.application.dto.response;

import com.canbagi.user.domain.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String email,
        List<ERole> roles,
        boolean active,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant updatedAt
){}
