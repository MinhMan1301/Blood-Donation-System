package com.blooddonation.controller;

import com.blooddonation.model.Account;
import com.blooddonation.service.AuthService;
import com.blooddonation.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DashboardController - Handles dashboard pages
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DataService dataService;

    @Autowired
    private AuthService authService;

    @GetMapping("")
    public String dashboard(Model model) {
        // Get statistics
        model.addAttribute("totalPatients", dataService.getTotalPatients());
        model.addAttribute("totalDoctors", dataService.getTotalDoctors());
        model.addAttribute("totalDonors", dataService.getTotalDonors());
        model.addAttribute("totalBloodBanks", dataService.getTotalBloodBanks());
        model.addAttribute("totalBloodUnits", dataService.getTotalBloodUnits());
        model.addAttribute("totalRequests", dataService.getTotalRequests());
        model.addAttribute("totalEvents", dataService.getTotalDonationEvents());
        model.addAttribute("totalAccounts", dataService.getTotalAccounts());

        return "dashboard";
    }

    @GetMapping("/doctor")
    public String doctorDashboard(Model model) {
        model.addAttribute("totalPatients", dataService.getTotalPatients());
        model.addAttribute("totalDonors", dataService.getTotalDonors());
        model.addAttribute("totalBloodBanks", dataService.getTotalBloodBanks());
        model.addAttribute("totalRequests", dataService.getTotalRequests());
        model.addAttribute("recentRequests", dataService.getRecentRequests(5)); // Get 5 recent requests

        return "dashboard-doctor";
    }

    @GetMapping("/patient")
    public String patientDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Account account = authService.getAccountByEmail(userDetails.getUsername());
        String patientId = account.getPatientId();

        model.addAttribute("totalDonors", dataService.getTotalDonors());
        model.addAttribute("totalBloodBanks", dataService.getTotalBloodBanks());
        model.addAttribute("totalBloodUnits", dataService.getTotalBloodUnits());
        model.addAttribute("patientRequests", dataService.getRequestsByPatientId(patientId));

        return "dashboard-patient";
    }

    @GetMapping("/donor")
    public String donorDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Account account = authService.getAccountByEmail(userDetails.getUsername());
        String donorId = account.getDonorId();

        model.addAttribute("totalBloodBanks", dataService.getTotalBloodBanks());
        model.addAttribute("totalEvents", dataService.getTotalDonationEvents());
        model.addAttribute("totalBloodUnits", dataService.getTotalBloodUnits());
        model.addAttribute("donorEvents", dataService.getDonationEventsByDonorId(donorId));

        return "dashboard-donor";
    }

    @GetMapping("/bloodbank")
    public String bloodBankDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Account account = authService.getAccountByEmail(userDetails.getUsername());
        String bankId = account.getBankId();

        model.addAttribute("totalBloodUnits", dataService.getTotalBloodUnits());
        model.addAttribute("totalRequests", dataService.getTotalRequests());
        model.addAttribute("totalDonors", dataService.getTotalDonors());
        model.addAttribute("totalEvents", dataService.getTotalDonationEvents());
        model.addAttribute("recentRequests", dataService.getRecentRequests(5));

        return "dashboard-bloodbank";
    }
}

