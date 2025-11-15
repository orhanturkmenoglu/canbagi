package com.canbagi.donor.application;

import com.canbagi.donor.application.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.application.dto.response.DonorProfileResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DonorService {

    DonorProfileResponseDTO createDonor(DonorProfileRequestDTO donorProfileRequestDTO);

    DonorProfileResponseDTO getDonorByEmail(String email);

    List<DonorProfileResponseDTO> getDonorsByBloodType(String bloodType);

    DonorProfileResponseDTO updateDonor(UUID donorId, DonorProfileRequestDTO donorProfileRequestDTO);

    void deleteDonorById(UUID donorId);
}
