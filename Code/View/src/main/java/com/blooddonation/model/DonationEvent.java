package com.blooddonation.model;



import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DonationEvent Entity Model
 * Represents donation events in the system
 */
public class DonationEvent {
    private String donationId;
    private LocalDate dateEvent;
    private String location;
    private BigDecimal volumeCollected;
    private String bankId;

    public DonationEvent() {
    }

    public DonationEvent(String donationId, LocalDate dateEvent, String location, BigDecimal volumeCollected, String bankId) {
        this.donationId = donationId;
        this.dateEvent = dateEvent;
        this.location = location;
        this.volumeCollected = volumeCollected;
        this.bankId = bankId;
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public LocalDate getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(LocalDate dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getVolumeCollected() {
        return volumeCollected;
    }

    public void setVolumeCollected(BigDecimal volumeCollected) {
        this.volumeCollected = volumeCollected;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }
}

