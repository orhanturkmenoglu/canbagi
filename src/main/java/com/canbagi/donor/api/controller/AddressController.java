package com.canbagi.donor.api.controller;

import com.canbagi.donor.application.AddressService;
import com.canbagi.donor.application.dto.request.AddressRequestDTO;
import com.canbagi.donor.application.dto.response.AddressResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@Validated @RequestBody AddressRequestDTO dto) {
        AddressResponseDTO response = addressService.createAddress(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable UUID id) {
        AddressResponseDTO response = addressService.getAddressById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        List<AddressResponseDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID id,
                                                            @Validated @RequestBody AddressRequestDTO dto) {
        AddressResponseDTO updated = addressService.updateAddress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
