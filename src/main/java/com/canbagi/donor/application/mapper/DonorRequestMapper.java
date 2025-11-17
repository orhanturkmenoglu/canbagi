package com.canbagi.donor.application.mapper;

import com.canbagi.donor.application.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.domain.DonorProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonorRequestMapper {
    DonorProfile toEntity(DonorProfileRequestDTO donorProfileRequestDTO);
}
