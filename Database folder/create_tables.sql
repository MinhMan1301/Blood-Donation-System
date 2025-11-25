-- Create Blood Donation Database Tables

USE blood_donation;

-- Drop tables if exist (in correct order)
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS Doctor_BloodBank;
DROP TABLE IF EXISTS Donors_DonationEvent;
DROP TABLE IF EXISTS Donation_Event;
DROP TABLE IF EXISTS Blood_Inventory;
DROP TABLE IF EXISTS BLOOD_BANK;
DROP TABLE IF EXISTS Request;
DROP TABLE IF EXISTS ACCOUNTT;
DROP TABLE IF EXISTS Donors;
DROP TABLE IF EXISTS Patients;
DROP TABLE IF EXISTS Doctor;
SET FOREIGN_KEY_CHECKS=1;

-- Doctors
CREATE TABLE Doctor (
    doctor_id          VARCHAR(20) PRIMARY KEY,
    ssn                VARCHAR(15) UNIQUE NOT NULL,
    full_name          VARCHAR(100) NOT NULL,
    email              VARCHAR(100) UNIQUE,
    phone              VARCHAR(20),
    specialization     VARCHAR(100) NOT NULL
);

-- Patients
CREATE TABLE Patients (
    patient_id      VARCHAR(20) PRIMARY KEY,
    ssn             VARCHAR(15) UNIQUE NOT NULL,
    full_name       VARCHAR(100) NOT NULL,
    DateOfBirth     DATE NOT NULL,
    age             SMALLINT,
    phone           VARCHAR(20),
    email           VARCHAR(100) UNIQUE,
    Gender          VARCHAR(10) NOT NULL,
    status          VARCHAR(50) DEFAULT 'New',
    donation_date   DATE
);

-- Donors
CREATE TABLE Donors (
    donors_id               VARCHAR(20) PRIMARY KEY,
    ssn                     VARCHAR(15) UNIQUE NOT NULL,
    full_name               VARCHAR(100) NOT NULL,
    DateOfBirth             DATE NOT NULL,
    age                     SMALLINT,
    phone                   VARCHAR(20),
    email                   VARCHAR(100) UNIQUE,
    Gender                  VARCHAR(10) NOT NULL,
    last_donation_date      DATE
);

-- ACCOUNT 
CREATE TABLE ACCOUNTT ( 
    id           VARCHAR(20) PRIMARY KEY, 
    email        VARCHAR(255) UNIQUE NOT NULL,
    password     VARCHAR(255) NOT NULL, 
    role         ENUM ('Patient', 'Doctor', 'Donor', 'BloodBank') NOT NULL,
    is_active    BOOLEAN DEFAULT TRUE,
    time_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    patient_id   VARCHAR(20),
    doctor_id    VARCHAR(20),
    donor_id     VARCHAR(20),
    bank_id      VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES Patients (patient_id),
    FOREIGN KEY (doctor_id)  REFERENCES Doctor (doctor_id),
    FOREIGN KEY (donor_id)   REFERENCES Donors (donors_id),
    FOREIGN KEY (bank_id)    REFERENCES BLOOD_BANK (bank_id)
);

-- Request table
CREATE TABLE Request (
    request_id             VARCHAR(20) PRIMARY KEY,
    bank_id                INT NOT NULL,
    blood_type             ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    quantity               INT,
    status                 ENUM('Pending', 'Approved', 'Fulfilled', 'Rejected') DEFAULT 'Pending',
    date_request           DATE NOT NULL,
    date_response          DATETIME NULL,
    fulfilled_inventory_id INT NULL,
    patient_id             VARCHAR(20),
    doctor_id              VARCHAR(20),
    donors_id              VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES Patients (patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor (doctor_id),
    FOREIGN KEY (donors_id) REFERENCES Donors (donors_id)
) ENGINE=InnoDB;

-- BLOOD_BANK 
CREATE TABLE BLOOD_BANK ( 
    bank_id         VARCHAR(20) PRIMARY KEY, 
    bank_name       VARCHAR(255) NOT NULL, 
    location        VARCHAR(255) NOT NULL, 
    contact_phone   VARCHAR(15),
    contact_email   VARCHAR(100), 
    volume          DECIMAL(10,2) DEFAULT 0,
    assigned_doctor VARCHAR(20),
    request_id      VARCHAR(20),
    FOREIGN KEY (request_id) REFERENCES Request (request_id)
);

-- BLOOD INVENTORY
CREATE TABLE Blood_Inventory (
    unit_id        VARCHAR(20) PRIMARY KEY,
    blood_type     ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    RH             ENUM('+', '-') NOT NULL,
    volume_Litter  DECIMAL(10,2) NOT NULL,
    donated_date   DATE NOT NULL,
    expired_date   DATE NOT NULL,
    status         ENUM('available', 'used', 'expired') DEFAULT 'available',
    bank_id        VARCHAR(20),
    FOREIGN KEY (bank_id) REFERENCES BLOOD_BANK (bank_id)
) ENGINE=InnoDB;

-- DONATION EVENT
CREATE TABLE Donation_Event (
    donation_id      VARCHAR(20) PRIMARY KEY,
    Date_event       DATE NOT NULL,
    location         VARCHAR(255) NOT NULL,
    volume_collected DECIMAL(10, 2) NOT NULL,
    bank_id          VARCHAR(20),
    FOREIGN KEY (bank_id) REFERENCES BLOOD_BANK (bank_id)
) ENGINE=InnoDB;

-- Associative tables
CREATE TABLE Donors_DonationEvent (
    donors_id       VARCHAR(20) NOT NULL,
    donation_id     VARCHAR(20) NOT NULL,
    PRIMARY KEY (donors_id, donation_id),
    FOREIGN KEY (donors_id) REFERENCES Donors(donors_id),
    FOREIGN KEY (donation_id) REFERENCES Donation_Event(donation_id)
);

CREATE TABLE Doctor_BloodBank (
    doctor_id       VARCHAR(20) NOT NULL,
    bank_id         VARCHAR(20) NOT NULL,
    PRIMARY KEY (doctor_id, bank_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id) ON DELETE CASCADE,
    FOREIGN KEY (bank_id) REFERENCES Blood_Bank(bank_id) ON DELETE CASCADE
);
