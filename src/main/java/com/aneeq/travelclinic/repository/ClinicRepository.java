package com.aneeq.travelclinic.repository;

import com.aneeq.travelclinic.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}