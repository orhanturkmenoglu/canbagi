package com.canbagi.donor.service.impl;

import com.canbagi.donor.service.AddressService;
import com.canbagi.donor.dto.request.AddressRequestDTO;
import com.canbagi.donor.dto.response.AddressResponseDTO;
import com.canbagi.donor.mapper.AddressMapper;
import com.canbagi.donor.mapper.AddressRequestMapper;
import com.canbagi.donor.model.Address;
import com.canbagi.donor.repository.AddressRepository;
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
    private final AddressMapper addressMapper;             // Entity → ResponseDTO
    private final AddressRequestMapper addressRequestMapper; // RequestDTO → Entity

    @Override
    @Transactional
    public AddressResponseDTO createAddress(AddressRequestDTO dto) {
        log.info("[CREATE] Address request received: {}", dto);

        // RequestDTO → Entity
        Address address = addressRequestMapper.toEntity(dto);
        Address saved = addressRepository.save(address);

        log.info("[CREATE] Address saved successfully with ID: {}", saved.getId());
        return addressMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDTO getAddressById(UUID addressId) {
        log.info("[GET] Fetching address by ID: {}", addressId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        return addressMapper.toDto(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAllAddresses() {
        log.info("[GET] Fetching all addresses");

        return addressMapper.toDtoList(addressRepository.findAll());
    }

    @Override
    @Transactional
    public AddressResponseDTO updateAddress(UUID addressId, AddressRequestDTO dto) {
        log.info("[UPDATE] Address update request received for ID: {}", addressId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressId));

        // Request DTO fields → Entity update
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setDistrict(dto.getDistrict());
        address.setStreet(dto.getStreet());
        address.setPostalCode(dto.getPostalCode());

        Address updated = addressRepository.save(address);
        log.info("[UPDATE] Address updated successfully: {}", addressId);

        return addressMapper.toDto(updated);
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
}
