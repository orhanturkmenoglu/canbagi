package com.canbagi.donor.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record DonorProfileResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String bloodType,
        boolean active,
        AddressResponseDTO address,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant createdDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant lastModifiedDate
) {}