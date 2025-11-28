package com.blooddonation.controller;

import com.blooddonation.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * DataController - Handles data display pages
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    // ========== PATIENTS ==========
    @GetMapping("/patients")
    public String patients(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("patients", dataService.searchPatientsByName(search));
            model.addAttribute("searchQuery", search);
        } else {
            model.addAttribute("patients", dataService.getAllPatients());
        }
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String patientDetail(@PathVariable String id, Model model) {
        model.addAttribute("patient", dataService.getPatientById(id));
        return "patient-detail";
    }

    // ========== DOCTORS ==========
    @GetMapping("/doctors")
    public String doctors(Model model, 
                         @RequestParam(required = false) String search,
                         @RequestParam(required = false) String searchType) {
        if (search != null && !search.isEmpty()) {
            if ("specialty".equals(searchType)) {
                model.addAttribute("doctors", dataService.searchDoctorsBySpecialty(search));
            } else {
                model.addAttribute("doctors", dataService.searchDoctorsByName(search));
            }
            model.addAttribute("searchQuery", search);
            model.addAttribute("searchType", searchType);
        } else {
            model.addAttribute("doctors", dataService.getAllDoctors());
        }
        return "doctors";
    }

    @GetMapping("/doctors/{id}")
    public String doctorDetail(@PathVariable String id, Model model) {
        model.addAttribute("doctor", dataService.getDoctorById(id));
        return "doctor-detail";
    }

    // ========== DONORS ==========
    @GetMapping("/donors")
    public String donors(Model model, 
                        @RequestParam(required = false) String search,
                        @RequestParam(required = false) String gender) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("donors", dataService.searchDonorsByName(search));
            model.addAttribute("searchQuery", search);
        } else if (gender != null && !gender.isEmpty()) {
            model.addAttribute("donors", dataService.getDonorsByGender(gender));
            model.addAttribute("genderFilter", gender);
        } else {
            model.addAttribute("donors", dataService.getAllDonors());
        }
        return "donors";
    }

    // ========== BLOOD BANKS ==========
    @GetMapping("/bloodbanks")
    public String bloodBanks(Model model, 
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) String location) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("bloodBanks", dataService.searchBloodBanksByName(search));
            model.addAttribute("searchQuery", search);
        } else if (location != null && !location.isEmpty()) {
            model.addAttribute("bloodBanks", dataService.getBloodBanksByLocation(location));
            model.addAttribute("locationFilter", location);
        } else {
            model.addAttribute("bloodBanks", dataService.getAllBloodBanks());
        }
        return "bloodbanks";
    }

    // ========== BLOOD INVENTORY ==========
    @GetMapping("/inventory")
    public String inventory(Model model, @RequestParam(required = false) String bloodType, @RequestParam(required = false) String status) {
        if (bloodType != null && !bloodType.isEmpty()) {
            model.addAttribute("inventory", dataService.getBloodInventoryByType(bloodType));
        } else if (status != null && !status.isEmpty()) {
            model.addAttribute("inventory", dataService.getBloodInventoryByStatus(status));
        } else {
            model.addAttribute("inventory", dataService.getAllBloodInventory());
        }
        return "inventory";
    }

    // ========== REQUESTS ==========
    @GetMapping("/requests")
    public String requests(Model model, @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            model.addAttribute("requests", dataService.getRequestsByStatus(status));
        } else {
            model.addAttribute("requests", dataService.getAllRequests());
        }
        return "requests";
    }

    // ========== DONATION EVENTS ==========
    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", dataService.getAllDonationEvents());
        return "events";
    }

    // ========== ACCOUNTS ==========
    @GetMapping("/accounts")
    public String accounts(Model model) {
        model.addAttribute("accounts", dataService.getAllAccounts());
        return "accounts";
    }

    // ========== STATISTICS ==========
    @GetMapping("/statistics")
    public String statistics(Model model) {
        // Basic counts
        model.addAttribute("totalDonors", dataService.getTotalDonors());
        model.addAttribute("totalDoctors", dataService.getTotalDoctors());
        model.addAttribute("totalBloodBanks", dataService.getTotalBloodBanks());
        model.addAttribute("totalBloodUnits", dataService.getTotalBloodUnits());

        // Blood type distribution
        java.util.List<java.util.Map<String, Object>> bloodTypes = new java.util.ArrayList<>();
        for (String type : new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"}) {
            java.util.Map<String, Object> typeData = new java.util.HashMap<>();
            typeData.put("bloodType", type);
            typeData.put("count", dataService.getBloodUnitsByType(type));
            bloodTypes.add(typeData);
        }
        model.addAttribute("bloodTypes", bloodTypes);

        // Recent donors
        model.addAttribute("recentDonors", dataService.getRecentDonors(5));

        // Inventory status
        model.addAttribute("availableUnits", dataService.getBloodUnitsByStatus("available"));
        model.addAttribute("usedUnits", dataService.getBloodUnitsByStatus("used"));
        model.addAttribute("expiredUnits", dataService.getBloodUnitsByStatus("expired"));

        return "statistics";
    }
}

