package com.canbagi.donor.application.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.donor.application.dto.response.DonorProfileResponseDTO;
import com.canbagi.donor.domain.DonorProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DonorMapper implements BaseMapper<DonorProfileResponseDTO, DonorProfile> {

    private final AddressMapper addressMapper;

    @Override
    public DonorProfileResponseDTO toDto(DonorProfile donorProfile) {
        log.debug("DonorProfileResponseDTO toDto method is called");

        if (donorProfile == null) return null;
        log.debug("DonorProfileResponseDTO toDto method is finished");

        return DonorProfileResponseDTO.builder()
                .id(donorProfile.getId())
                .firstName(donorProfile.getFirstName())
                .lastName(donorProfile.getLastName())
                .email(donorProfile.getEmail())
                .phone(donorProfile.getPhone())
                .address(addressMapper.toDto(donorProfile.getAddress()))
                .bloodType(donorProfile.getBloodType())
                .active(donorProfile.getActive())
                .createdDate(donorProfile.getCreatedDate())
                .lastModifiedDate(donorProfile.getLastModifiedDate())
                .build();
    }

    @Override
    public DonorProfile toEntity(DonorProfileResponseDTO donorProfileResponseDTO) {
        log.debug("DonorProfileResponseDTO toEntity method is called");

        if (donorProfileResponseDTO == null) return null;

        return DonorProfile.builder()
                .firstName(donorProfileResponseDTO.firstName())
                .lastName(donorProfileResponseDTO.lastName())
                .email(donorProfileResponseDTO.email())
                .phone(donorProfileResponseDTO.phone())
                .bloodType(donorProfileResponseDTO.bloodType())
                .address(addressMapper.toEntity(donorProfileResponseDTO.address()))
                .active(donorProfileResponseDTO.active())
                .build();
    }
}
