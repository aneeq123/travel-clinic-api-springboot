package com.aneeq.travelclinic.service;

import com.aneeq.travelclinic.dto.ClinicRequestDTO;
import com.aneeq.travelclinic.dto.ClinicResponseDTO;
import com.aneeq.travelclinic.entity.Clinic;
import com.aneeq.travelclinic.exception.ResourceNotFoundException;
import com.aneeq.travelclinic.repository.ClinicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public ClinicResponseDTO createClinic(ClinicRequestDTO dto) {
        Clinic clinic = new Clinic(dto.getName(), dto.getAddress(), dto.getPhone(), dto.getEmail());
        Clinic saved = clinicRepository.save(clinic);
        return toResponse(saved);
    }

    public Page<ClinicResponseDTO> getClinics(Pageable pageable) {
        return clinicRepository.findAll(pageable).map(this::toResponse);
    }

    public ClinicResponseDTO getClinicById(Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found: " + id));
        return toResponse(clinic);
    }

    public ClinicResponseDTO updateClinic(Long id, ClinicRequestDTO dto) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found: " + id));

        clinic.setName(dto.getName());
        clinic.setAddress(dto.getAddress());
        clinic.setPhone(dto.getPhone());
        clinic.setEmail(dto.getEmail());

        Clinic saved = clinicRepository.save(clinic);
        return toResponse(saved);
    }

    public void deleteClinic(Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found: " + id));
        clinicRepository.delete(clinic);
    }

    private ClinicResponseDTO toResponse(Clinic clinic) {
        ClinicResponseDTO response = new ClinicResponseDTO();
        response.setId(clinic.getId());
        response.setName(clinic.getName());
        response.setAddress(clinic.getAddress());
        response.setPhone(clinic.getPhone());
        response.setEmail(clinic.getEmail());
        return response;
    }
}