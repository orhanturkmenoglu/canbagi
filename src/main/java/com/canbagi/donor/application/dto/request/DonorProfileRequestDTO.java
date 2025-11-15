package com.canbagi.donor.application.dto.request;

import com.canbagi.donor.domain.Address;
import com.canbagi.donor.domain.DonorProfile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DonorProfileRequestDTO {

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2,message ="First name size must be min 2 characters ")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, message ="Last name size must be min 2 characters ")
    private String lastName;


    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 10, message ="Phone size must be 10 characters")
    private String phone;

    @NotBlank(message = "Blood type cannot be blank")
    private String bloodType;

    @Builder.Default
    private Boolean active = Boolean.FALSE;

    private AddressRequestDTO address;

}
