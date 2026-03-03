package com.aneeq.travelclinic.controller;

import com.aneeq.travelclinic.dto.AppointmentRequestDTO;
import com.aneeq.travelclinic.dto.AppointmentResponseDTO;
import com.aneeq.travelclinic.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/clinics/{clinicId}/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentResponseDTO create(@PathVariable Long clinicId,
                                         @Valid @RequestBody AppointmentRequestDTO dto) {
        return appointmentService.createAppointment(clinicId, dto);
    }

    @GetMapping
    public Page<AppointmentResponseDTO> list(@PathVariable Long clinicId,
                                             @RequestParam(required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                             LocalDateTime from,
                                             @RequestParam(required = false)
                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                             LocalDateTime to,
                                             Pageable pageable) {
        return appointmentService.getAppointments(clinicId, Optional.ofNullable(from), Optional.ofNullable(to), pageable);
    }

    @PutMapping("/{appointmentId}")
    public AppointmentResponseDTO update(@PathVariable Long clinicId,
                                         @PathVariable Long appointmentId,
                                         @Valid @RequestBody AppointmentRequestDTO dto) {
        return appointmentService.updateAppointment(clinicId, appointmentId, dto);
    }

    @DeleteMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long clinicId,
                       @PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(clinicId, appointmentId);
    }
    
    
    @GetMapping("/ping")
    public String ping() {
        return "appointments controller is working";
    }
}