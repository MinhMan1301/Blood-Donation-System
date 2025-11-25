package com.blooddonation.dao;

import com.blooddonation.model.Donor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DonorDAO - Data Access Object for Donor entity
 */
@Repository
public class DonorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class DonorRowMapper implements RowMapper<Donor> {
        @Override
        public Donor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Donor donor = new Donor();
            donor.setDonorsId(rs.getString("donors_id"));
            donor.setSsn(rs.getString("ssn"));
            donor.setFullName(rs.getString("full_name"));
            donor.setDateOfBirth(rs.getDate("DateOfBirth") != null ? 
                rs.getDate("DateOfBirth").toLocalDate() : null);
            donor.setAge(rs.getInt("age"));
            donor.setPhone(rs.getString("phone"));
            donor.setEmail(rs.getString("email"));
            donor.setGender(rs.getString("Gender"));
            donor.setLastDonationDate(rs.getDate("last_donation_date") != null ? 
                rs.getDate("last_donation_date").toLocalDate() : null);
            return donor;
        }
    }

    public List<Donor> findAll() {
        String sql = "SELECT * FROM Donors ORDER BY donors_id";
        return jdbcTemplate.query(sql, new DonorRowMapper());
    }

    public Donor findById(String id) {
        String sql = "SELECT * FROM Donors WHERE donors_id = ?";
        List<Donor> donors = jdbcTemplate.query(sql, new DonorRowMapper(), id);
        return donors.isEmpty() ? null : donors.get(0);
    }

    public List<Donor> searchByName(String name) {
        String sql = "SELECT * FROM Donors WHERE full_name LIKE ? ORDER BY full_name";
        return jdbcTemplate.query(sql, new DonorRowMapper(), "%" + name + "%");
    }

    public List<Donor> findByGender(String gender) {
        String sql = "SELECT * FROM Donors WHERE Gender = ? ORDER BY donors_id";
        return jdbcTemplate.query(sql, new DonorRowMapper(), gender);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Donors";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

