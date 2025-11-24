package com.canbagi.donor.application.dto.response;

import com.canbagi.donor.domain.enums.BloodType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Schema(description = "Donor Profile Response DTO")
public record DonorProfileResponseDTO(
        @Schema(description = "Donor ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "First Name", example = "John")
        String firstName,
        @Schema(description = "Last Name", example = "Doe")
        String lastName,
        @Schema(description = "Email", example = "john.doe@example.com")
        String email,
        @Schema(description = "Phone Number", example = "+1234567890")
        String phone,

        @Schema(description = "Blood Type", example = "A_POSITIVE")
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        BloodType bloodType,

        @Schema(description = "Active Status", example = "true")
        @JsonFormat(shape = JsonFormat.Shape.BOOLEAN)
        boolean active,
        @Schema(description = "Address")
        AddressResponseDTO address,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        @Schema(description = "Created Date", example = "2023-01-01T00:00:00Z")
        Instant createdDate,
        @Schema(description = "Last Modified Date", example = "2023-01-02T00:00:00Z")
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant lastModifiedDate
) {}