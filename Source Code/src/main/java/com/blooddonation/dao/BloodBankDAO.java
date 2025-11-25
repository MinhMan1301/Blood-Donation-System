package com.blooddonation.dao;

import com.blooddonation.model.BloodBank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * BloodBankDAO - Data Access Object for BloodBank entity
 */
@Repository
public class BloodBankDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class BloodBankRowMapper implements RowMapper<BloodBank> {
        @Override
        public BloodBank mapRow(ResultSet rs, int rowNum) throws SQLException {
            BloodBank bloodBank = new BloodBank();
            bloodBank.setBankId(rs.getString("bank_id"));
            bloodBank.setBankName(rs.getString("bank_name"));
            bloodBank.setLocation(rs.getString("location"));
            bloodBank.setContactPhone(rs.getString("contact_phone"));
            bloodBank.setContactEmail(rs.getString("contact_email"));
            bloodBank.setVolume(rs.getBigDecimal("volume"));
            bloodBank.setAssignedDoctor(rs.getString("assigned_doctor"));
            bloodBank.setRequestId(rs.getString("request_id"));
            return bloodBank;
        }
    }

    public List<BloodBank> findAll() {
        String sql = "SELECT * FROM BLOOD_BANK ORDER BY bank_id";
        return jdbcTemplate.query(sql, new BloodBankRowMapper());
    }

    public BloodBank findById(String id) {
        String sql = "SELECT * FROM BLOOD_BANK WHERE bank_id = ?";
        List<BloodBank> bloodBanks = jdbcTemplate.query(sql, new BloodBankRowMapper(), id);
        return bloodBanks.isEmpty() ? null : bloodBanks.get(0);
    }

    public List<BloodBank> searchByName(String name) {
        String sql = "SELECT * FROM BLOOD_BANK WHERE bank_name LIKE ? ORDER BY bank_name";
        return jdbcTemplate.query(sql, new BloodBankRowMapper(), "%" + name + "%");
    }

    public List<BloodBank> findByLocation(String location) {
        String sql = "SELECT * FROM BLOOD_BANK WHERE location LIKE ? ORDER BY bank_id";
        return jdbcTemplate.query(sql, new BloodBankRowMapper(), "%" + location + "%");
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM BLOOD_BANK";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

