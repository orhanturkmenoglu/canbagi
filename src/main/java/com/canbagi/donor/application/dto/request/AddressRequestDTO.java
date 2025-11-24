package com.canbagi.donor.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

@Getter
@Setter
@Builder
@Schema(description = "Address Request DTO")
public class AddressRequestDTO {
    @Schema(description = "Country", example = "Turkey")
    @NotBlank(message = "Country cannot be blank")
    @Builder.Default()
    private String country = LocaleContextHolder.getLocale().getDisplayCountry();

    @Schema(description = "City", example = "Istanbul")
    @NotBlank(message = "City cannot be blank")
    private String city;

    @Schema(description = "District", example = "Kadikoy")
    @NotBlank(message = "District cannot be blank")
    private String district;

    @Schema(description = "Street", example = "123 Main St")
    @NotBlank(message = "Street cannot be blank")
    private String street;

    @Schema(description = "Postal Code", example = "12345")
    private String postalCode;
}
