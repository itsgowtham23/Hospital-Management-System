CREATE TABLE prescription (
    prescriptionID INT AUTO_INCREMENT PRIMARY KEY,  -- ID with auto-increment
    patientName VARCHAR(255) NOT NULL,  -- Patient's name
    appointmentID INT NOT NULL,  -- Appointment ID, a foreign key reference to the appointment table
    description TEXT,  -- Description of the prescription (can be large text)
    doctorName VARCHAR(255) NOT NULL,  -- Doctor's name
    FOREIGN KEY (appointmentID) REFERENCES appointment(appointment_id)  -- Foreign key to appointment table
);