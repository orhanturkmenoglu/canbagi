package com.canbagi.donor.mapper;

import com.canbagi.donor.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.model.DonorProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonorRequestMapper {
    DonorProfile toEntity(DonorProfileRequestDTO donorProfileRequestDTO);
}
