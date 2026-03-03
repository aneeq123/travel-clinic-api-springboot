package com.aneeq.travelclinic.repository;

import com.aneeq.travelclinic.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByClinicId(Long clinicId, Pageable pageable);

    Page<Appointment> findByClinicIdAndAppointmentDateTimeBetween(
            Long clinicId,
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable
    );
}