package com.canbagi.donor.application.mapper;

import com.canbagi.donor.application.dto.request.AddressRequestDTO;
import com.canbagi.donor.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressRequestMapper  {
    Address toEntity(AddressRequestDTO addressRequestDTO);
}
