package com.matheus.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheus.clinic.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}