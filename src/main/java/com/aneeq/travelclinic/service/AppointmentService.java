package com.aneeq.travelclinic.service;

import com.aneeq.travelclinic.dto.AppointmentRequestDTO;
import com.aneeq.travelclinic.dto.AppointmentResponseDTO;
import com.aneeq.travelclinic.entity.Appointment;
import com.aneeq.travelclinic.entity.Clinic;
import com.aneeq.travelclinic.exception.ResourceNotFoundException;
import com.aneeq.travelclinic.repository.AppointmentRepository;
import com.aneeq.travelclinic.repository.ClinicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClinicRepository clinicRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ClinicRepository clinicRepository) {
        this.appointmentRepository = appointmentRepository;
        this.clinicRepository = clinicRepository;
    }

    public AppointmentResponseDTO createAppointment(Long clinicId, AppointmentRequestDTO dto) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new ResourceNotFoundException("Clinic not found: " + clinicId));

        Appointment appointment = new Appointment();
        appointment.setPatientName(dto.getPatientName());
        appointment.setPatientEmail(dto.getPatientEmail());
        appointment.setVaccineType(dto.getVaccineType());
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());
        appointment.setClinic(clinic);

        Appointment saved = appointmentRepository.save(appointment);
        return toResponse(saved);
    }

    public Page<AppointmentResponseDTO> getAppointments(Long clinicId,
                                                        Optional<LocalDateTime> from,
                                                        Optional<LocalDateTime> to,
                                                        Pageable pageable) {

        if (from.isPresent() && to.isPresent() && from.get().isAfter(to.get())) {
            throw new IllegalArgumentException("'from' must be before 'to'");
        }

        if (!clinicRepository.existsById(clinicId)) {
            throw new ResourceNotFoundException("Clinic not found: " + clinicId);
        }

        Page<Appointment> page;

        if (from.isPresent() && to.isPresent()) {
            page = appointmentRepository.findByClinicIdAndAppointmentDateTimeBetween(
                    clinicId, from.get(), to.get(), pageable
            );
        } else {
            page = appointmentRepository.findByClinicId(clinicId, pageable);
        }

        return page.map(this::toResponse);
    }

    public AppointmentResponseDTO updateAppointment(Long clinicId, Long appointmentId, AppointmentRequestDTO dto) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + appointmentId));

        if (!appointment.getClinic().getId().equals(clinicId)) {
            throw new ResourceNotFoundException("Appointment " + appointmentId + " not found for clinic " + clinicId);
        }

        appointment.setPatientName(dto.getPatientName());
        appointment.setPatientEmail(dto.getPatientEmail());
        appointment.setVaccineType(dto.getVaccineType());
        appointment.setAppointmentDateTime(dto.getAppointmentDateTime());

        Appointment saved = appointmentRepository.save(appointment);
        return toResponse(saved);
    }

    public void deleteAppointment(Long clinicId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found: " + appointmentId));

        if (!appointment.getClinic().getId().equals(clinicId)) {
            throw new ResourceNotFoundException("Appointment " + appointmentId + " not found for clinic " + clinicId);
        }

        appointmentRepository.delete(appointment);
    }

    private AppointmentResponseDTO toResponse(Appointment appointment) {
        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setId(appointment.getId());
        response.setPatientName(appointment.getPatientName());
        response.setPatientEmail(appointment.getPatientEmail());
        response.setVaccineType(appointment.getVaccineType());
        response.setAppointmentDateTime(appointment.getAppointmentDateTime());
        response.setClinicId(appointment.getClinic().getId());
        return response;
    }
}