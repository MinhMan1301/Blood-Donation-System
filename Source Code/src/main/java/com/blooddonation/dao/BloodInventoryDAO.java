package com.blooddonation.dao;

import com.blooddonation.model.BloodInventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * BloodInventoryDAO - Data Access Object for BloodInventory entity
 */
@Repository
public class BloodInventoryDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class BloodInventoryRowMapper implements RowMapper<BloodInventory> {
        @Override
        public BloodInventory mapRow(ResultSet rs, int rowNum) throws SQLException {
            BloodInventory inventory = new BloodInventory();
            inventory.setUnitId(rs.getString("unit_id"));
            inventory.setBloodType(rs.getString("blood_type"));
            inventory.setRh(rs.getString("RH"));
            inventory.setVolumeLitter(rs.getBigDecimal("volume_Litter"));
            inventory.setDonatedDate(rs.getDate("donated_date") != null ? 
                rs.getDate("donated_date").toLocalDate() : null);
            inventory.setExpiredDate(rs.getDate("expired_date") != null ? 
                rs.getDate("expired_date").toLocalDate() : null);
            inventory.setStatus(rs.getString("status"));
            inventory.setBankId(rs.getString("bank_id"));
            return inventory;
        }
    }

    public List<BloodInventory> findAll() {
        String sql = "SELECT * FROM Blood_Inventory ORDER BY unit_id";
        return jdbcTemplate.query(sql, new BloodInventoryRowMapper());
    }

    public BloodInventory findById(String id) {
        String sql = "SELECT * FROM Blood_Inventory WHERE unit_id = ?";
        List<BloodInventory> inventories = jdbcTemplate.query(sql, new BloodInventoryRowMapper(), id);
        return inventories.isEmpty() ? null : inventories.get(0);
    }

    public List<BloodInventory> findByBloodType(String bloodType) {
        String sql = "SELECT * FROM Blood_Inventory WHERE blood_type = ? ORDER BY unit_id";
        return jdbcTemplate.query(sql, new BloodInventoryRowMapper(), bloodType);
    }

    public List<BloodInventory> findByStatus(String status) {
        String sql = "SELECT * FROM Blood_Inventory WHERE status = ? ORDER BY unit_id";
        return jdbcTemplate.query(sql, new BloodInventoryRowMapper(), status);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Blood_Inventory";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countByBloodType(String bloodType) {
        String sql = "SELECT COUNT(*) FROM Blood_Inventory WHERE blood_type = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, bloodType);
    }
}

