package com.blooddonation.model;



import java.time.LocalDate;

/**
 * Donor Entity Model
 * Represents blood donors in the system
 */
public class Donor {
    private String donorsId;
    private String ssn;
    private String fullName;
    private LocalDate dateOfBirth;
    private Integer age;
    private String phone;
    private String email;
    private String gender;
    private LocalDate lastDonationDate;

    public Donor() {
    }

    public Donor(String donorsId, String ssn, String fullName, LocalDate dateOfBirth, Integer age, String phone, String email, String gender, LocalDate lastDonationDate) {
        this.donorsId = donorsId;
        this.ssn = ssn;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.lastDonationDate = lastDonationDate;
    }

    public String getDonorsId() {
        return donorsId;
    }

    public void setDonorsId(String donorsId) {
        this.donorsId = donorsId;
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

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }
}

