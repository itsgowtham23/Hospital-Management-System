package com.hospital.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hospital.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
	
	
	List<Appointment> findByDoctorName(String doctorName);
	
	
	List<Appointment> findByPatientName(String patientName);
	
	@Modifying 
	@Query("update Appointment a set a.confirmed = ?1 where a.appointment_id = ?2")
	int setConfirmation(String confitmation, Long id);
	

	@Modifying 
	@Query("update Appointment a set a.prescription = ?1 where a.appointment_id = ?2")
	int setPrescription(String prescription, Integer id);
	
	@Query(value="select * from Appointment a where a.appointment_date =?1 AND a.doctor_name =?2", nativeQuery=true)
	List<Appointment> findByDate(String date,String doctorName);
}
