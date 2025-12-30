package com.canbagi.donor.mapper;

import com.canbagi.common.base.BaseMapper;
import com.canbagi.donor.dto.response.DonorProfileResponseDTO;
import com.canbagi.donor.model.DonorProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonorMapper extends BaseMapper<DonorProfileResponseDTO, DonorProfile> {
}
