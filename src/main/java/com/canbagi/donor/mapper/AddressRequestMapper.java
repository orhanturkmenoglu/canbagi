package com.canbagi.donor.mapper;

import com.canbagi.donor.dto.request.AddressRequestDTO;
import com.canbagi.donor.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressRequestMapper  {
    Address toEntity(AddressRequestDTO addressRequestDTO);
}
