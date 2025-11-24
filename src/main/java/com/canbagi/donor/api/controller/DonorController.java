package com.canbagi.donor.api.controller;

import com.canbagi.donor.application.DonorService;
import com.canbagi.donor.application.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.application.dto.response.DonorProfileResponseDTO;
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
@RequestMapping("/donors")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Donor Controller", description = "Donor API")
public class DonorController {

    private final DonorService donorService;

    @Operation(summary = "Create a new donor", description = "Creates a new donor profile")
    @ApiResponse(responseCode = "201", description = "Donor created successfully")
    @PostMapping
    public ResponseEntity<DonorProfileResponseDTO> createDonor(@Validated @RequestBody DonorProfileRequestDTO dto) {
        DonorProfileResponseDTO response = donorService.createDonor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a donor by ID", description = "Retrieves a donor profile by ID")
    @ApiResponse(responseCode = "200", description = "Donor retrieved successfully")
    @GetMapping("/{id}")
    public ResponseEntity<DonorProfileResponseDTO> getDonorById(@PathVariable UUID id) {
        DonorProfileResponseDTO response = donorService.getDonorByEmail(id.toString()); // eğer email ile arama yapılacaksa parametre değiştirilebilir
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a donor by email", description = "Retrieves a donor profile by email")
    @ApiResponse(responseCode = "200", description = "Donor retrieved successfully")
    @GetMapping("/email")
    public ResponseEntity<DonorProfileResponseDTO> getDonorByEmail(@RequestParam String email) {
        DonorProfileResponseDTO response = donorService.getDonorByEmail(email);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get donors by blood type", description = "Retrieves a list of donor profiles by blood type")
    @ApiResponse(responseCode = "200", description = "Donors retrieved successfully")
    @GetMapping("/blood-type/{bloodType}")
    public ResponseEntity<List<DonorProfileResponseDTO>> getDonorsByBloodType(@PathVariable String bloodType) {
        List<DonorProfileResponseDTO> donors = donorService.getDonorsByBloodType(bloodType);
        return ResponseEntity.ok(donors);
    }

    @Operation(summary = "Update a donor", description = "Updates an existing donor profile")
    @ApiResponse(responseCode = "200", description = "Donor updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<DonorProfileResponseDTO> updateDonor(@PathVariable UUID id,
                                                               @Validated @RequestBody DonorProfileRequestDTO dto) {
        DonorProfileResponseDTO updated = donorService.updateDonor(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete a donor", description = "Deletes a donor profile by ID")
    @ApiResponse(responseCode = "204", description = "Donor deleted successfully")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable UUID id) {
        donorService.deleteDonorById(id);
        return ResponseEntity.noContent().build();
    }
}
