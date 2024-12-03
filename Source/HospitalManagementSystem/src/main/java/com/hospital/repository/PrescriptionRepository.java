package com.hospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription,Integer> {

	List<Prescription> findByPatientName(String patientName);
}
