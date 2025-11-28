package com.blooddonation.model;



/**
 * Doctor Entity Model
 * Represents doctors in the system
 */
public class Doctor {
    private String doctorId;
    private String ssn;
    private String fullName;
    private String email;
    private String phone;
    private String specialization;

    public Doctor() {
    }

    public Doctor(String doctorId, String ssn, String fullName, String email, String phone, String specialization) {
        this.doctorId = doctorId;
        this.ssn = ssn;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}

