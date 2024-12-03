CREATE TABLE appointment (
    appointment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    patientName VARCHAR(255) NOT NULL,
    doctor_name VARCHAR(255) NOT NULL,
    appointment_date VARCHAR(255) NOT NULL,
    prescription TEXT,
    confirmed ENUM('YES', 'NO', 'PENDING') NOT NULL
);