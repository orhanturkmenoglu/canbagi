package com.canbagi.donor.application.impl;

import com.canbagi.donor.application.DonorService;
import com.canbagi.donor.application.dto.request.AddressRequestDTO;
import com.canbagi.donor.application.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.application.dto.response.DonorProfileResponseDTO;
import com.canbagi.donor.application.mapper.DonorMapper;
import com.canbagi.donor.application.mapper.DonorRequestMapper;
import com.canbagi.donor.domain.Address;
import com.canbagi.donor.domain.DonorProfile;
import com.canbagi.donor.infrastructure.AddressRepository;
import com.canbagi.donor.infrastructure.DonorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final AddressRepository addressRepository;
    private final DonorMapper donorMapper;               // Entity ↔ DTO ↔ Response
    private final DonorRequestMapper donorRequestMapper; // Request → Entity

    @Override
    @Transactional
    public DonorProfileResponseDTO createDonor(DonorProfileRequestDTO request) {
        log.info("[CREATE] Donor request received: {}", request);

        if (donorRepository.existByEmail(request.getEmail())) {
            throw new RuntimeException("Donor already exists with email: " + request.getEmail());
        }

        // Request → Entity conversion
        DonorProfile donor = donorRequestMapper.toEntity(request);
        log.debug("[CREATE] Donor mapped from request: {}", donor);

        // Address otomatik maplendi, override gerekmedikçe dokunma
        DonorProfile savedDonor = donorRepository.save(donor);

        log.info("[CREATE] Donor saved successfully: {}", savedDonor.getId());

        return donorMapper.toDto(savedDonor);
    }

    @Override
    @Transactional(readOnly = true)
    public DonorProfileResponseDTO getDonorByEmail(String email) {
        log.info("[GET] Searching donor by email: {}", email);

        DonorProfile donor = donorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donor not found with email: " + email));

        log.info("[GET] Donor found: {}", donor.getId());

        return donorMapper.toDto(donor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorProfileResponseDTO> getDonorsByBloodType(String bloodType) {
        log.info("[GET] Searching donors by blood type: {}", bloodType);

        List<DonorProfile> donors = donorRepository.findByBloodType(bloodType);

        return donorMapper.toDtoList(donors);
    }

    @Override
    @Transactional
    public DonorProfileResponseDTO updateDonor(UUID donorId, DonorProfileRequestDTO request) {
        log.info("[UPDATE] Donor update request received for ID: {}", donorId);

        DonorProfile donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + donorId));

        log.debug("[UPDATE] Existing donor: {}", donor);

        // Basic fields update
        donor.setFirstName(request.getFirstName());
        donor.setLastName(request.getLastName());
        donor.setEmail(request.getEmail());
        donor.setPhone(request.getPhone());
        donor.setBloodType(request.getBloodType());

        Address address = donor.getAddress();
        updatedAddress(request);

        donor.setAddress(address);


        log.debug("[UPDATE] Address updated: {}", address);

        DonorProfile savedDonor = donorRepository.save(donor);
        log.info("[UPDATE] Donor updated successfully: {}", donorId);

        return donorMapper.toDto(savedDonor);
    }


    @Override
    @Transactional
    public void deleteDonorById(UUID donorId) {
        log.info("[DELETE] Delete request received for donor ID: {}", donorId);

        DonorProfile donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + donorId));

        Address address = donor.getAddress();

        donorRepository.delete(donor);
        log.info("[DELETE] Donor deleted successfully: {}", donorId);

        if (address != null) {
            addressRepository.delete(address);
            log.debug("[DELETE] Associated address deleted: {}", address.getId());
        }
    }

    private static void updatedAddress(DonorProfileRequestDTO request) {
        log.debug("[UPDATE] Updating address: {}", request.getAddress());
        AddressRequestDTO addressRequestDTO = request.getAddress();
        log.debug("[UPDATE] Address request: {}", addressRequestDTO);
        if (addressRequestDTO != null) {
            addressRequestDTO.setCountry(addressRequestDTO.getCountry());
            addressRequestDTO.setCity(addressRequestDTO.getCity());
            addressRequestDTO.setDistrict(addressRequestDTO.getDistrict());
            addressRequestDTO.setStreet(addressRequestDTO.getStreet());
            addressRequestDTO.setPostalCode(addressRequestDTO.getPostalCode());
        }
    }

}
