package com.blooddonation.dao;

import com.blooddonation.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * AccountDAO - Data Access Object for Account entity
 * Handles all database operations for accounts
 */
@Repository
public class AccountDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper for Account
    private static class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account account = new Account();
            account.setId(rs.getString("id"));
            account.setEmail(rs.getString("email"));
            account.setPassword(rs.getString("password"));
            account.setRole(rs.getString("role"));
            account.setIsActive(rs.getBoolean("is_active"));
            
            // Handle nullable time_created to prevent NullPointerException
            java.sql.Timestamp timestamp = rs.getTimestamp("time_created");
            if (timestamp != null) {
                account.setTimeCreated(timestamp.toLocalDateTime());
            }
            
            account.setPatientId(rs.getString("patient_id"));
            account.setDoctorId(rs.getString("doctor_id"));
            account.setDonorId(rs.getString("donor_id"));
            account.setBankId(rs.getString("bank_id"));
            return account;
        }
    }

    /**
     * Find all accounts
     */
    public List<Account> findAll() {
        String sql = "SELECT * FROM ACCOUNTT";
        return jdbcTemplate.query(sql, new AccountRowMapper());
    }

    /**
     * Find account by ID
     */
    public Account findById(String id) {
        String sql = "SELECT * FROM ACCOUNTT WHERE id = ?";
        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), id);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    /**
     * Find account by email
     */
    public Account findByEmail(String email) {
        String sql = "SELECT * FROM ACCOUNTT WHERE email = ?";
        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), email);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    /**
     * Find account by email and password (for login)
     */
    public Account findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM ACCOUNTT WHERE email = ? AND password = ?";
        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), email, password);
        return accounts.isEmpty() ? null : accounts.get(0);
    }

    /**
     * Count total accounts
     */
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM ACCOUNTT";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Count accounts by role
     */
    public int countByRole(String role) {
        String sql = "SELECT COUNT(*) FROM ACCOUNTT WHERE role = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, role);
    }
}

