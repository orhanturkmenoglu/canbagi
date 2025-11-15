package com.canbagi.donor.application.impl;

import com.canbagi.donor.application.AddressService;
import com.canbagi.donor.application.dto.request.AddressRequestDTO;
import com.canbagi.donor.application.dto.response.AddressResponseDTO;
import com.canbagi.donor.domain.Address;
import com.canbagi.donor.infrastructure.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public AddressResponseDTO createAddress(AddressRequestDTO dto) {
        log.info("[CREATE] Address request received: {}", dto);

        Address address = mapToEntity(dto);
        address = addressRepository.save(address);

        log.info("[CREATE] Address saved successfully with ID: {}", address.getId());
        return mapToResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressById(UUID addressId) {
        log.info("[GET] Fetching address by ID: {}", addressId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        log.info("[GET] Address found: {}", addressId);
        return mapToResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAllAddresses() {
        log.info("[GET] Fetching all addresses");

        return addressRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public AddressResponseDTO updateAddress(UUID addressId, AddressRequestDTO dto) {
        log.info("[UPDATE] Address update request received for ID: {}", addressId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setState(dto.getDistrict());
        address.setStreet(dto.getStreet());
        address.setPostalCode(dto.getPostalCode());

        addressRepository.save(address);
        log.info("[UPDATE] Address updated successfully: {}", addressId);

        return mapToResponse(address);
    }

    @Override
    @Transactional
    public void deleteAddress(UUID addressId) {
        log.info("[DELETE] Delete request for address ID: {}", addressId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        addressRepository.delete(address);
        log.info("[DELETE] Address deleted successfully: {}", addressId);
    }

    // ----------------- Mapper -----------------
    private Address mapToEntity(AddressRequestDTO dto) {
        Address address = new Address();
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setState(dto.getDistrict());
        address.setStreet(dto.getStreet());
        address.setPostalCode(dto.getPostalCode());
        return address;
    }

    private AddressResponseDTO mapToResponse(Address address) {
        return new AddressResponseDTO(
                address.getCountry(),
                address.getCity(),
                address.getState(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCreatedDate(),
                address.getLastModifiedDate()
        );
    }
}
