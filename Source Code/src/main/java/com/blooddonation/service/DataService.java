package com.blooddonation.service;

import com.blooddonation.dao.*;
import com.blooddonation.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DataService - Main Data Service
 * Provides business logic for data retrieval and queries
 */
@Service
public class DataService {

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private DoctorDAO doctorDAO;

    @Autowired
    private DonorDAO donorDAO;

    @Autowired
    private BloodBankDAO bloodBankDAO;

    @Autowired
    private BloodInventoryDAO bloodInventoryDAO;

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private DonationEventDAO donationEventDAO;

    @Autowired
    private AccountDAO accountDAO;

    // ========== PATIENT SERVICES ==========
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }

    public Patient getPatientById(String id) {
        return patientDAO.findById(id);
    }

    public List<Patient> searchPatientsByName(String name) {
        return patientDAO.searchByName(name);
    }

    public int getTotalPatients() {
        return patientDAO.countAll();
    }

    // ========== DOCTOR SERVICES ==========
    public List<Doctor> getAllDoctors() {
        return doctorDAO.findAll();
    }

    public Doctor getDoctorById(String id) {
        return doctorDAO.findById(id);
    }

    public List<Doctor> searchDoctorsBySpecialty(String specialty) {
        return doctorDAO.searchBySpecialty(specialty);
    }

    public List<Doctor> searchDoctorsByName(String name) {
        return doctorDAO.searchByName(name);
    }

    public int getTotalDoctors() {
        return doctorDAO.countAll();
    }

    // ========== DONOR SERVICES ==========
    public List<Donor> getAllDonors() {
        return donorDAO.findAll();
    }

    public Donor getDonorById(String id) {
        return donorDAO.findById(id);
    }

    public List<Donor> searchDonorsByName(String name) {
        return donorDAO.searchByName(name);
    }

    public List<Donor> getDonorsByGender(String gender) {
        return donorDAO.findByGender(gender);
    }

    public List<Donor> getRecentDonors(int limit) {
        List<Donor> allDonors = donorDAO.findAll();
        return allDonors.stream()
                .filter(d -> d.getLastDonationDate() != null)
                .sorted((d1, d2) -> d2.getLastDonationDate().compareTo(d1.getLastDonationDate()))
                .limit(limit)
                .collect(java.util.stream.Collectors.toList());
    }

    public int getTotalDonors() {
        return donorDAO.countAll();
    }

    // ========== BLOOD BANK SERVICES ==========
    public List<BloodBank> getAllBloodBanks() {
        return bloodBankDAO.findAll();
    }

    public BloodBank getBloodBankById(String id) {
        return bloodBankDAO.findById(id);
    }

    public List<BloodBank> searchBloodBanksByName(String name) {
        return bloodBankDAO.searchByName(name);
    }

    public List<BloodBank> getBloodBanksByLocation(String location) {
        return bloodBankDAO.findByLocation(location);
    }

    public int getTotalBloodBanks() {
        return bloodBankDAO.countAll();
    }

    // ========== BLOOD INVENTORY SERVICES ==========
    public List<BloodInventory> getAllBloodInventory() {
        return bloodInventoryDAO.findAll();
    }

    public BloodInventory getBloodInventoryById(String id) {
        return bloodInventoryDAO.findById(id);
    }

    public List<BloodInventory> getBloodInventoryByType(String bloodType) {
        return bloodInventoryDAO.findByBloodType(bloodType);
    }

    public List<BloodInventory> getBloodInventoryByStatus(String status) {
        return bloodInventoryDAO.findByStatus(status);
    }

    public int getTotalBloodUnits() {
        return bloodInventoryDAO.countAll();
    }

    public int getBloodUnitsByType(String bloodType) {
        return bloodInventoryDAO.countByBloodType(bloodType);
    }

    public int getBloodUnitsByStatus(String status) {
        return bloodInventoryDAO.findByStatus(status).size();
    }

    // ========== REQUEST SERVICES ==========
    public List<Request> getAllRequests() {
        return requestDAO.findAll();
    }

    public Request getRequestById(String id) {
        return requestDAO.findById(id);
    }

    public List<Request> getRecentRequests(int limit) {
        return requestDAO.findRecent(limit);
    }


    public List<Request> getRequestsByPatientId(String patientId) {
        return requestDAO.findByPatientId(patientId);
    }


    public List<Request> getRequestsByStatus(String status) {
        return requestDAO.findByStatus(status);
    }

    public int getTotalRequests() {
        return requestDAO.countAll();
    }

    // ========== DONATION EVENT SERVICES ==========
    public List<DonationEvent> getAllDonationEvents() {
        return donationEventDAO.findAll();
    }

    public DonationEvent getDonationEventById(String id) {
        return donationEventDAO.findById(id);
    }

    public int getTotalDonationEvents() {
        return donationEventDAO.countAll();
    }

    public List<DonationEvent> getDonationEventsByDonorId(String donorId) {
        return donationEventDAO.findByDonorId(donorId);
    }

    // ========== ACCOUNT SERVICES ==========
    public List<Account> getAllAccounts() {
        return accountDAO.findAll();
    }

    public int getTotalAccounts() {
        return accountDAO.countAll();
    }
}

