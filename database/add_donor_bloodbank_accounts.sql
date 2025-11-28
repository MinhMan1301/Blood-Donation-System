-- Add Donor and BloodBank roles to existing database
USE blood_donation;

-- First, we need to alter the ACCOUNTT table to support new roles
-- Drop the existing ENUM constraint and recreate with new values
ALTER TABLE ACCOUNTT MODIFY COLUMN role ENUM('Patient', 'Doctor', 'Donor', 'BloodBank') NOT NULL;

-- Add new columns for donor_id and bank_id
-- Check if columns exist first, if error occurs, they already exist
SET @exist_donor := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = 'blood_donation' AND TABLE_NAME = 'ACCOUNTT' AND COLUMN_NAME = 'donor_id');
SET @exist_bank := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS 
    WHERE TABLE_SCHEMA = 'blood_donation' AND TABLE_NAME = 'ACCOUNTT' AND COLUMN_NAME = 'bank_id');

SET @sql_donor = IF(@exist_donor = 0, 'ALTER TABLE ACCOUNTT ADD COLUMN donor_id VARCHAR(20)', 'SELECT "donor_id already exists"');
SET @sql_bank = IF(@exist_bank = 0, 'ALTER TABLE ACCOUNTT ADD COLUMN bank_id VARCHAR(20)', 'SELECT "bank_id already exists"');

PREPARE stmt_donor FROM @sql_donor;
EXECUTE stmt_donor;
DEALLOCATE PREPARE stmt_donor;

PREPARE stmt_bank FROM @sql_bank;
EXECUTE stmt_bank;
DEALLOCATE PREPARE stmt_bank;

-- Add foreign key constraints (ignore if already exist)
SET @fk_donor := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
    WHERE TABLE_SCHEMA = 'blood_donation' AND TABLE_NAME = 'ACCOUNTT' AND CONSTRAINT_NAME = 'fk_donor');
SET @fk_bank := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS 
    WHERE TABLE_SCHEMA = 'blood_donation' AND TABLE_NAME = 'ACCOUNTT' AND CONSTRAINT_NAME = 'fk_bank');

SET @sql_fk_donor = IF(@fk_donor = 0, 
    'ALTER TABLE ACCOUNTT ADD CONSTRAINT fk_donor FOREIGN KEY (donor_id) REFERENCES Donors(donors_id)', 
    'SELECT "fk_donor already exists"');
SET @sql_fk_bank = IF(@fk_bank = 0, 
    'ALTER TABLE ACCOUNTT ADD CONSTRAINT fk_bank FOREIGN KEY (bank_id) REFERENCES BLOOD_BANK(bank_id)', 
    'SELECT "fk_bank already exists"');

PREPARE stmt_fk_donor FROM @sql_fk_donor;
EXECUTE stmt_fk_donor;
DEALLOCATE PREPARE stmt_fk_donor;

PREPARE stmt_fk_bank FROM @sql_fk_bank;
EXECUTE stmt_fk_bank;
DEALLOCATE PREPARE stmt_fk_bank;

-- Create accounts for some donors (using same password hash as other accounts: password123)
-- Password hash: $2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa

INSERT INTO ACCOUNTT (id, email, password, role, is_active, patient_id, doctor_id, donor_id, bank_id) VALUES
('ACCDON001', 'nguyenthianh.don1@example.com', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'Donor', TRUE, NULL, NULL, 'DON001', NULL),
('ACCDON002', 'tranvanbinh.don2@example.com', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'Donor', TRUE, NULL, NULL, 'DON002', NULL),
('ACCDON003', 'lethicam.don3@example.com', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'Donor', TRUE, NULL, NULL, 'DON003', NULL),
('ACCDON004', 'phamvandung.don4@example.com', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'Donor', TRUE, NULL, NULL, 'DON004', NULL),
('ACCDON005', 'hoangthihanh.don5@example.com', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'Donor', TRUE, NULL, NULL, 'DON005', NULL);

-- Create accounts for blood banks
INSERT INTO ACCOUNTT (id, email, password, role, is_active, patient_id, doctor_id, donor_id, bank_id) VALUES
('ACCBANK001', 'central@bloodbank.vn', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'BloodBank', TRUE, NULL, NULL, NULL, 'BANK001'),
('ACCBANK002', 'saigon@bloodbank.vn', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'BloodBank', TRUE, NULL, NULL, NULL, 'BANK002'),
('ACCBANK003', 'danang@bloodbank.vn', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'BloodBank', TRUE, NULL, NULL, NULL, 'BANK003'),
('ACCBANK004', 'hue@bloodbank.vn', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'BloodBank', TRUE, NULL, NULL, NULL, 'BANK004'),
('ACCBANK005', 'cantho@bloodbank.vn', '$2a$10$U45isQ7.fdYtoSjTSrAw5OZsTzCSzr1qXbrLYVxM9in2PeO9KPBEa', 'BloodBank', TRUE, NULL, NULL, NULL, 'BANK005');

-- Verify the new accounts
SELECT id, email, role, donor_id, bank_id FROM ACCOUNTT WHERE role IN ('Donor', 'BloodBank');
