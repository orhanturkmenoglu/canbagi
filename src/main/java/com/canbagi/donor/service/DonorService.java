package com.canbagi.donor.service;

import com.canbagi.donor.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.dto.response.DonorProfileResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DonorService {

    DonorProfileResponseDTO createDonor(DonorProfileRequestDTO donorProfileRequestDTO);

    DonorProfileResponseDTO getDonorByEmail(String email);

    List<DonorProfileResponseDTO> getDonorsByBloodType(String bloodType);

    DonorProfileResponseDTO updateDonor(UUID donorId, DonorProfileRequestDTO donorProfileRequestDTO);

    void deleteDonorById(UUID donorId);
}
