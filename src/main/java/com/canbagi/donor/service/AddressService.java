package com.canbagi.donor.service;

import com.canbagi.donor.dto.request.AddressRequestDTO;
import com.canbagi.donor.dto.response.AddressResponseDTO;

import java.util.List;
import java.util.UUID;

public interface AddressService {

    AddressResponseDTO createAddress(AddressRequestDTO addressRequestDTO);

    AddressResponseDTO getAddressById(UUID addressId);

    List<AddressResponseDTO> getAllAddresses();

    AddressResponseDTO updateAddress(UUID addressId, AddressRequestDTO addressRequestDTO);

    void deleteAddress(UUID addressId);
}