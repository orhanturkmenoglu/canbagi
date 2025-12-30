package com.canbagi.donor.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.donor.dto.response.AddressResponseDTO;
import com.canbagi.donor.model.Address;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AddressMapper extends BaseMapper<AddressResponseDTO, Address> {
}
