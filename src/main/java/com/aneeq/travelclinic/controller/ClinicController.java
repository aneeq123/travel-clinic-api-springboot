package com.aneeq.travelclinic.controller;

import com.aneeq.travelclinic.dto.ClinicRequestDTO;
import com.aneeq.travelclinic.dto.ClinicResponseDTO;
import com.aneeq.travelclinic.service.ClinicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClinicResponseDTO createClinic(@Valid @RequestBody ClinicRequestDTO dto) {
        return clinicService.createClinic(dto);
    }

    @GetMapping
    public Page<ClinicResponseDTO> getClinics(Pageable pageable) {
        return clinicService.getClinics(pageable);
    }

    @GetMapping("/{id}")
    public ClinicResponseDTO getClinicById(@PathVariable Long id) {
        return clinicService.getClinicById(id);
    }

    @PutMapping("/{id}")
    public ClinicResponseDTO updateClinic(@PathVariable Long id, @Valid @RequestBody ClinicRequestDTO dto) {
        return clinicService.updateClinic(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
    }
}