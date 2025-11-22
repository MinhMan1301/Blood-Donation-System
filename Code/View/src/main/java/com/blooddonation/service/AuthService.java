package com.blooddonation.service;

import com.blooddonation.dao.AccountDAO;
import com.blooddonation.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * AuthService - Authentication Service
 * Handles user authentication and authorization
 */
@Service
public class AuthService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("=== LOGIN ATTEMPT ===");
        logger.info("Email: {}", email);
        
        Account account = accountDAO.findByEmail(email);
        if (account == null) {
            logger.error("Account not found for email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        
        logger.info("Account found - ID: {}, Role: {}, DoctorID: {}", 
            account.getId(), account.getRole(), account.getDoctorId());
        logger.info("Password hash from DB: {}", account.getPassword());
        
        return new User(account.getEmail(), account.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(account.getRole())));
    }



    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        return accountDAO.findByEmail(email) != null;
    }

    /**
     * Get account by ID
     */
    public Account getAccountById(String id) {
        return accountDAO.findById(id);
    }

    /**
     * Get account by email
     */
    public Account getAccountByEmail(String email) {
        return accountDAO.findByEmail(email);
    }
}

