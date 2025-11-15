package com.canbagi.donor.api.controller;

import com.canbagi.donor.application.DonorService;
import com.canbagi.donor.application.dto.request.DonorProfileRequestDTO;
import com.canbagi.donor.application.dto.response.DonorProfileResponseDTO;
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
public class DonorController {

    private final DonorService donorService;

    @PostMapping
    public ResponseEntity<DonorProfileResponseDTO> createDonor(@Validated @RequestBody DonorProfileRequestDTO dto) {
        DonorProfileResponseDTO response = donorService.createDonor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorProfileResponseDTO> getDonorById(@PathVariable UUID id) {
        DonorProfileResponseDTO response = donorService.getDonorByEmail(id.toString()); // eğer email ile arama yapılacaksa parametre değiştirilebilir
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email")
    public ResponseEntity<DonorProfileResponseDTO> getDonorByEmail(@RequestParam String email) {
        DonorProfileResponseDTO response = donorService.getDonorByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/blood-type/{bloodType}")
    public ResponseEntity<List<DonorProfileResponseDTO>> getDonorsByBloodType(@PathVariable String bloodType) {
        List<DonorProfileResponseDTO> donors = donorService.getDonorsByBloodType(bloodType);
        return ResponseEntity.ok(donors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonorProfileResponseDTO> updateDonor(@PathVariable UUID id,
                                                               @Validated @RequestBody DonorProfileRequestDTO dto) {
        DonorProfileResponseDTO updated = donorService.updateDonor(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable UUID id) {
        donorService.deleteDonorById(id);
        return ResponseEntity.noContent().build();
    }
}
