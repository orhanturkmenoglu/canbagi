package com.canbagi.donor.application.dto.request;

import com.canbagi.donor.domain.Address;
import com.canbagi.donor.domain.DonorProfile;
import com.canbagi.donor.domain.enums.BloodType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Donor Profile Request DTO")
public class DonorProfileRequestDTO {

    @Schema(description = "First Name", example = "John")
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2,message ="First name size must be min 2 characters ")
    private String firstName;

    @Schema(description = "Last Name", example = "Doe")
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, message ="Last name size must be min 2 characters ")
    private String lastName;


    @Schema(description = "Email", example = "john.doe@example.com")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @Schema(description = "Phone Number", example = "+1234567890")
    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 10, message ="Phone size must be 10 characters")
    private String phone;

    @Schema(description = "Blood Type", example = "A_POSITIVE")
    @NotNull(message = "Blood type cannot be null")
    private BloodType bloodType;

    @Schema(description = "Address")
    private AddressRequestDTO address;

}
