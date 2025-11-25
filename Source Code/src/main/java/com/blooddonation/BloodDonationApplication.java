package com.blooddonation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class for Blood Donation Management System
 * 
 * @author Blood Donation Team
 * @version 1.0
 */
@SpringBootApplication
public class BloodDonationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BloodDonationApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Blood Donation System Started Successfully!");
        System.out.println("Access at: http://localhost:8081");
        System.out.println("========================================\n");
    }
}

