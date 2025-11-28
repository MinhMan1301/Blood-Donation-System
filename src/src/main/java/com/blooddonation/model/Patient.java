package com.blooddonation.model;



import java.time.LocalDate;

/**
 * Patient Entity Model
 * Represents patients in the system
 */
public class Patient {
    private String patientId;
    private String ssn;
    private String fullName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String phone;
    private String email;
    private String gender;
    private String status;
    private LocalDate donationDate;

    public Patient() {
    }

    public Patient(String patientId, String ssn, String fullName, LocalDate dateOfBirth, Integer age, String phone, String email, String gender, String status, LocalDate donationDate) {
        this.patientId = patientId;
        this.ssn = ssn;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.status = status;
        this.donationDate = donationDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }
}

