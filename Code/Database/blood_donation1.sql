CREATE DATABASE IF NOT EXISTS blood_donation;
USE blood_donation;
SELECT * FROM Doctor;
SELECT * FROM Patients;
SELECT * FROM Donors;
SELECT * FROM Blood_Inventory;
SELECT * FROM BLOOD_BANK;
SELECT * FROM Donation_Event;
SELECT * FROM Donors_DonationEvent;
SELECT * FROM Doctor_BloodBank;
SELECT * FROM Request;
SELECT * FROM ACCOUNTT;
-- Doctors
CREATE TABLE IF NOT EXISTS Doctor (
    doctor_id          VARCHAR(20) PRIMARY KEY,
    ssn                 VARCHAR(15) UNIQUE NOT NULL,
    full_name           VARCHAR(100) NOT NULL,
    email               VARCHAR(100) UNIQUE,
    phone               VARCHAR(20),
    specialization      VARCHAR(100) NOT NULL,
    CONSTRAINT check_specialization_format CHECK (specialization IS NOT NULL AND specialization <> '')
);

-- Patients
CREATE TABLE IF NOT EXISTS Patients (
    patient_id      VARCHAR(20) PRIMARY KEY,
    ssn             VARCHAR(15) UNIQUE NOT NULL,
    full_name       VARCHAR(100) NOT NULL,
    DateOfBirth     DATE NOT NULL,
    age             SMALLINT,
    phone           VARCHAR(20),
    email           VARCHAR(100) UNIQUE,
    Gender          VARCHAR(10) NOT NULL,
    status          VARCHAR(50) DEFAULT 'New',
    donation_date   DATE,
    CONSTRAINT check_patient_age CHECK (age > 0),
    CONSTRAINT check_patient_gender CHECK (Gender IN ('Male', 'Female', 'Other'))
);

-- Donors
CREATE TABLE IF NOT EXISTS Donors (
    donors_id               VARCHAR(20) PRIMARY KEY,
    ssn                     VARCHAR(15) UNIQUE NOT NULL,
    full_name               VARCHAR(100) NOT NULL,
    DateOfBirth             DATE NOT NULL,
    age                     SMALLINT,
    phone                   VARCHAR(20),
    email                   VARCHAR(100) UNIQUE,
    Gender                  VARCHAR(10) NOT NULL,
    last_donation_date      DATE,
    CONSTRAINT check_donor_age CHECK (age >= 18 AND age <= 100),
    CONSTRAINT check_donor_gender CHECK (Gender IN ('Male', 'Female', 'Other'))
);

-- ACCOUNT 
CREATE TABLE IF NOT EXISTS ACCOUNTT( 
	id 			 VARCHAR(20) 										PRIMARY KEY, 
	email 		 VARCHAR(255)										UNIQUE NOT NULL,
	password 	 VARCHAR(255) 										NOT NULL, 
	role 		 ENUM ('Patient', 'Doctor')	NOT NULL,
	is_active 	 BOOLEAN 											DEFAULT TRUE,
	time_created TIMESTAMP 											DEFAULT CURRENT_TIMESTAMP,
    patient_id 	 VARCHAR(20),
    doctor_id    VARCHAR(20),
	FOREIGN KEY (patient_id) REFERENCES Patients (patient_id),
	FOREIGN KEY (doctor_id)  REFERENCES Doctor (doctor_id)
);

-- request table
CREATE TABLE IF NOT EXISTS Request (
    request_id 	 VARCHAR(20) 											PRIMARY KEY,
    bank_id 	 INT 													NOT NULL,
    blood_type   ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    quantity 	 INT 													CHECK (quantity > 0),
    status 		 ENUM('Pending', 'Approved', 'Fulfilled', 'Rejected')   DEFAULT 'Pending',
    date_request DATE 													NOT NULL,
    date_response DATETIME 												NULL,
    fulfilled_inventory_id INT 											NULL,
    patient_id VARCHAR(20),
    doctor_id VARCHAR(20),
    donors_id VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES Patients (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor (doctor_id),
    FOREIGN KEY (donors_id) REFERENCES Donors (donors_id)
) ENGINE=InnoDB;

-- BLOOD_BANK 
CREATE TABLE IF NOT EXISTS BLOOD_BANK ( 
	bank_id 		VARCHAR(20)   PRIMARY KEY, 
	bank_name 		VARCHAR(255)  NOT NULL, 
	location  		VARCHAR(255)  NOT NULL, 
	contact_phone 	VARCHAR(15),
	contact_email 	VARCHAR(100), 
	volume 			DECIMAL(10,2) DEFAULT 0,
	assigned_doctor VARCHAR(20),
    request_id 		VARCHAR(20),
    FOREIGN KEY (request_id) REFERENCES Request (request_id)
);

-- BLOOD INVENTORY
CREATE TABLE IF NOT EXISTS Blood_Inventory(
    unit_id 		VARCHAR(20) 											PRIMARY KEY,
    blood_type 		ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-')  NOT NULL,
    RH 			 	ENUM('+', '-') 											NOT NULL,
    volume_Litter 	DECIMAL(10,2) 											NOT NULL,
    donated_date 	DATE 													NOT NULL,
    expired_date 	DATE 													NOT NULL,
    status ENUM('available', 'used', 'expired') 							DEFAULT 'available',
    bank_id 		VARCHAR(20),
    CONSTRAINT chk_dates CHECK (expired_date > donated_date),
    FOREIGN KEY (bank_id) REFERENCES BLOOD_BANK (bank_id)
) ENGINE=InnoDB;

-- DONATION EVENT
CREATE TABLE IF NOT EXISTS Donation_Event (
    donation_id 	 VARCHAR(20) PRIMARY KEY,
    Date_event 		 DATE NOT NULL,
    location 		 VARCHAR(255) NOT NULL,
    volume_collected DECIMAL(10, 2) NOT NULL,
    bank_id 		 VARCHAR(20),
    FOREIGN KEY (bank_id) REFERENCES BLOOD_BANK (bank_id)
) ENGINE=InnoDB;


-- Associative table
CREATE TABLE IF NOT EXISTS Donors_DonationEvent (
    donors_id       VARCHAR(20) NOT NULL,
    donation_id     VARCHAR(20) NOT NULL,
    PRIMARY KEY (donors_id, donation_id),
    FOREIGN KEY (donors_id) REFERENCES Donors(donors_id),
    FOREIGN KEY (donation_id) REFERENCES Donation_Event(donation_id)
);

CREATE TABLE IF NOT EXISTS Doctor_BloodBank (
    doctor_id       VARCHAR(20) NOT NULL,
    bank_id         VARCHAR(20) NOT NULL,
    PRIMARY KEY (doctor_id, bank_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id) ON DELETE CASCADE,
    FOREIGN KEY (bank_id) REFERENCES Blood_Bank(bank_id) ON DELETE CASCADE
);