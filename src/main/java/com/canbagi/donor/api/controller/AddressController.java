package com.canbagi.donor.api.controller;

import com.canbagi.donor.application.AddressService;
import com.canbagi.donor.application.dto.request.AddressRequestDTO;
import com.canbagi.donor.application.dto.response.AddressResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Address Controller", description = "Address API")
public class AddressController {

    private final AddressService addressService;

    @Operation(summary = "Create a new address", description = "Creates a new address")
    @ApiResponse(responseCode = "201", description = "Address created successfully")
    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@Validated @RequestBody AddressRequestDTO dto) {
        AddressResponseDTO response = addressService.createAddress(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get an address by ID", description = "Retrieves an address by its ID")
    @ApiResponse(responseCode = "200", description = "Address retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable UUID id) {
        AddressResponseDTO response = addressService.getAddressById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all addresses", description = "Retrieves all addresses")
    @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully")
    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        List<AddressResponseDTO> addresses = addressService.getAllAddresses();
        return ResponseEntity.ok(addresses);
    }

    @Operation(summary = "Update an address", description = "Updates an existing address")
    @ApiResponse(responseCode = "200", description = "Address updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable UUID id,
                                                            @Validated @RequestBody AddressRequestDTO dto) {
        AddressResponseDTO updated = addressService.updateAddress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete an address", description = "Deletes an address by ID")
    @ApiResponse(responseCode = "204", description = "Address deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }
}
