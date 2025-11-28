package com.blooddonation.model;



import java.math.BigDecimal;

/**
 * BloodBank Entity Model
 * Represents blood banks in the system
 */
public class BloodBank {
    private String bankId;
    private String bankName;
    private String location;
    private String contactPhone;
    private String contactEmail;
    private BigDecimal volume;
    private String assignedDoctor;
    private String requestId;

    public BloodBank() {
    }

    public BloodBank(String bankId, String bankName, String location, String contactPhone, String contactEmail, BigDecimal volume, String assignedDoctor, String requestId) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.location = location;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.volume = volume;
        this.assignedDoctor = assignedDoctor;
        this.requestId = requestId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(String assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

