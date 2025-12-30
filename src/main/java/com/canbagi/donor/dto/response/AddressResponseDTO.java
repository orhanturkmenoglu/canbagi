package com.canbagi.donor.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Schema(description = "Address Response DTO")
public record AddressResponseDTO(
        @Schema(description = "Country", example = "Turkey")
        String country,

        @Schema(description = "City", example = "Istanbul")
        String city,

        @Schema(description = "District", example = "Kadikoy")
        String district,

        @Schema(description = "Street", example = "123 Main St")
        String street,

        @Schema(description = "Postal Code", example = "12345")
        String postalCode,

        @Schema(description = "Created Date", example = "2023-01-01T00:00:00Z")
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant createdDate,

        @Schema(description = "Last Modified Date", example = "2023-01-02T00:00:00Z")
        @JsonFormat(shape = JsonFormat.Shape.STRING,
                pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant lastModifiedDate
) {
}