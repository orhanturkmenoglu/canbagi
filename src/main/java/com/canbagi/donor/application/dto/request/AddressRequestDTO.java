package com.canbagi.donor.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;

@Getter
@Setter
@Builder
public class AddressRequestDTO {
    @Builder.Default()
    private String country = LocaleContextHolder.getLocale().getDisplayCountry();
    @NotBlank(message = "City cannot be blank")
    private String city;
    @NotBlank(message = "District cannot be blank")
    private String district;
    @NotBlank(message = "Street cannot be blank")
    private String street;
    private String postalCode;
}
