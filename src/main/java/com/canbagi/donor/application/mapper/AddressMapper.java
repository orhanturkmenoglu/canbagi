package com.canbagi.donor.application.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.donor.application.dto.response.AddressResponseDTO;
import com.canbagi.donor.domain.Address;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<AddressResponseDTO, Address> {
}
