package com.canbagi.donor.application.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.donor.application.dto.response.AddressResponseDTO;
import com.canbagi.donor.domain.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddressMapper implements BaseMapper<AddressResponseDTO, Address> {

    @Override
    public AddressResponseDTO toDto(Address address) {
        log.debug("AddressResponseDTO toDto method is called");

        if (address == null) return null;
        log.debug("AddressResponseDTO toDto method is finished");

        return AddressResponseDTO.builder()

                .build();
    }

    @Override
    public Address toEntity(AddressResponseDTO addressResponseDTO) {
        log.debug("AddressResponseDTO toEntity method is called");
        if (addressResponseDTO == null) return null;

        log.debug("AddressResponseDTO toEntity method is finished");

        return Address.builder()
                .country(addressResponseDTO.country())
                .city(addressResponseDTO.city())
                .district(addressResponseDTO.district())
                .street(addressResponseDTO.street())
                .postalCode(addressResponseDTO.postalCode())
                .build();
    }
}
