package com.blooddonation.model;



import java.time.LocalDateTime;

/**
 * Account Entity Model
 * Represents user accounts in the system
 */
public class Account {
    private String id;
    private String email;
    private String password;
    private String role; // Patient, Doctor, Donor, BloodBank
    private Boolean isActive;
    private LocalDateTime timeCreated;
    private String patientId;
    private String doctorId;
    private String donorId;
    private String bankId;

    public Account() {
    }

    public Account(String id, String email, String password, String role, Boolean isActive, LocalDateTime timeCreated, 
                   String patientId, String doctorId, String donorId, String bankId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.timeCreated = timeCreated;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.donorId = donorId;
        this.bankId = bankId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}

