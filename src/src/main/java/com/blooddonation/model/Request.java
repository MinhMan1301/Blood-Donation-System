package com.blooddonation.model;



import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Request Entity Model
 * Represents blood requests in the system
 */
public class Request {
    private String requestId;
    private Integer bankId;
    private String bloodType;
    private Integer quantity;
    private String status;
    private LocalDate dateRequest;
    private LocalDateTime dateResponse;
    private Integer fulfilledInventoryId;
    private String patientId;
    private String doctorId;
    private String donorsId;

    public Request() {
    }

    public Request(String requestId, Integer bankId, String bloodType, Integer quantity, String status, LocalDate dateRequest, LocalDateTime dateResponse, Integer fulfilledInventoryId, String patientId, String doctorId, String donorsId) {
        this.requestId = requestId;
        this.bankId = bankId;
        this.bloodType = bloodType;
        this.quantity = quantity;
        this.status = status;
        this.dateRequest = dateRequest;
        this.dateResponse = dateResponse;
        this.fulfilledInventoryId = fulfilledInventoryId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.donorsId = donorsId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDate dateRequest) {
        this.dateRequest = dateRequest;
    }

    public LocalDateTime getDateResponse() {
        return dateResponse;
    }

    public void setDateResponse(LocalDateTime dateResponse) {
        this.dateResponse = dateResponse;
    }

    public Integer getFulfilledInventoryId() {
        return fulfilledInventoryId;
    }

    public void setFulfilledInventoryId(Integer fulfilledInventoryId) {
        this.fulfilledInventoryId = fulfilledInventoryId;
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

    public String getDonorsId() {
        return donorsId;
    }

    public void setDonorsId(String donorsId) {
        this.donorsId = donorsId;
    }
}

