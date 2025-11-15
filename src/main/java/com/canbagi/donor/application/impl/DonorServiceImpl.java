package com.canbagi.donor.application.impl;

import com.canbagi.donor.application.DonorService;
import com.canbagi.donor.application.dto.request.AddressRequestDTO;
import com.canbagi.donor.application.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.application.dto.response.AddressResponseDTO;
import com.canbagi.donor.application.dto.response.DonorProfileResponseDTO;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public DonorProfileResponseDTO createDonor(DonorProfileRequestDTO donorProfileRequestDTO) {
        log.info("[CREATE] Donor request received: {}", donorProfileRequestDTO);

        // Address entity oluştur
        Address address = mapToAddress(donorProfileRequestDTO.getAddress());
        address = addressRepository.save(address);
        log.debug("[CREATE] Address saved: {}", address);

        // Donor entity oluştur
        DonorProfile donor = mapToDonor(donorProfileRequestDTO);
        donor.setId(UUID.randomUUID());
        donor.setActive(true);
        donor.setAddress(address);

        DonorProfile savedDonor = donorRepository.save(donor);
        log.info("[CREATE] Donor saved successfully with ID: {}", savedDonor.getId());

        return mapToResponse(savedDonor);
    }

    @Override
    @Transactional(readOnly = true)
    public DonorProfileResponseDTO getDonorByEmail(String email) {
        log.info("[GET] Searching donor by email: {}", email);

        DonorProfile donor = donorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donor not found with email: " + email));

        log.info("[GET] Donor found: {}", donor.getId());
        return mapToResponse(donor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorProfileResponseDTO> getDonorsByBloodType(String bloodType) {
        log.info("[GET] Searching donors by blood type: {}", bloodType);

        List<DonorProfile> donors = donorRepository.findByBloodType(bloodType);
        return donors.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public DonorProfileResponseDTO updateDonor(UUID donorId, DonorProfileRequestDTO donorProfileRequestDTO) {
        log.info("[UPDATE] Donor update request received for ID: {}", donorId);

        DonorProfile existingDonor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + donorId));

        log.debug("[UPDATE] Existing donor: {}", existingDonor);

        // Update basic fields
        existingDonor.setFirstName(donorProfileRequestDTO.getFirstName());
        existingDonor.setLastName(donorProfileRequestDTO.getLastName());
        existingDonor.setEmail(donorProfileRequestDTO.getEmail());
        existingDonor.setPhone(donorProfileRequestDTO.getPhone());
        existingDonor.setBloodType(donorProfileRequestDTO.getBloodType());

        // Update address
        Address address = existingDonor.getAddress();
        updateAddress(address, donorProfileRequestDTO.getAddress());
        addressRepository.save(address);
        log.debug("[UPDATE] Address updated: {}", address);

        donorRepository.save(existingDonor);
        log.info("[UPDATE] Donor updated successfully: {}", donorId);

        return mapToResponse(existingDonor);
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

    // ----------------- Mapper / Helper Methods -----------------

    private Address mapToAddress(AddressRequestDTO dto) {
        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getDistrict());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
        return address;
    }

    private void updateAddress(Address address, AddressRequestDTO dto) {
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getDistrict());
        address.setPostalCode(dto.getPostalCode());
        address.setCountry(dto.getCountry());
    }

    private DonorProfile mapToDonor(DonorProfileRequestDTO dto) {
        DonorProfile donor = new DonorProfile();
        donor.setFirstName(dto.getFirstName());
        donor.setLastName(dto.getLastName());
        donor.setEmail(dto.getEmail());
        donor.setPhone(dto.getPhone());
        donor.setBloodType(dto.getBloodType());
        donor.setActive(dto.getActive());
        return donor;
    }

    private DonorProfileResponseDTO mapToResponse(DonorProfile donor) {
        Address addr = donor.getAddress();
        AddressResponseDTO addressResponse = new AddressResponseDTO(
                addr.getCountry(),
                addr.getCity(),
                addr.getState(),
                addr.getStreet(),
                addr.getPostalCode(),
                addr.getCreatedDate(),
                addr.getLastModifiedDate()
        );

        return new DonorProfileResponseDTO(
                donor.getId(),
                donor.getFirstName(),
                donor.getLastName(),
                donor.getEmail(),
                donor.getPhone(),
                donor.getBloodType(),
                donor.getActive(),
                donor.getAddress() != null ? addressResponse : null,
                donor.getCreatedDate(),
                donor.getLastModifiedDate()
        );
    }

}
