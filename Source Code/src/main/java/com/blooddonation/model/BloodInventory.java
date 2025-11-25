package com.blooddonation.model;



import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * BloodInventory Entity Model
 * Represents blood inventory/units in the system
 */
public class BloodInventory {
    private String unitId;
    private String bloodType;
    private String rh;
    private BigDecimal volumeLitter;
    private LocalDate donatedDate;

    public static final java.util.List<String> bloodTypeValues = java.util.Arrays.asList("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
    private LocalDate expiredDate;
    private String status;
    private String bankId;

    public BloodInventory() {
    }

    public BloodInventory(String unitId, String bloodType, String rh, BigDecimal volumeLitter, LocalDate donatedDate, LocalDate expiredDate, String status, String bankId) {
        this.unitId = unitId;
        this.bloodType = bloodType;
        this.rh = rh;
        this.volumeLitter = volumeLitter;
        this.donatedDate = donatedDate;
        this.expiredDate = expiredDate;
        this.status = status;
        this.bankId = bankId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getRh() {
        return rh;
    }

    public void setRh(String rh) {
        this.rh = rh;
    }

    public BigDecimal getVolumeLitter() {
        return volumeLitter;
    }

    public void setVolumeLitter(BigDecimal volumeLitter) {
        this.volumeLitter = volumeLitter;
    }

    public LocalDate getDonatedDate() {
        return donatedDate;
    }

    public void setDonatedDate(LocalDate donatedDate) {
        this.donatedDate = donatedDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}

