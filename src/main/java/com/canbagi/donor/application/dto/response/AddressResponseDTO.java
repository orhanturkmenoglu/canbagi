package com.canbagi.donor.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record AddressResponseDTO(
        String country,
        String city,
        String district,
        String street,
        String postalCode,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant createdDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant lastModifiedDate
) {}