package com.blooddonation.dao;

import com.blooddonation.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * PatientDAO - Data Access Object for Patient entity
 */
@Repository
public class PatientDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class PatientRowMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Patient patient = new Patient();
            patient.setPatientId(rs.getString("patient_id"));
            patient.setSsn(rs.getString("ssn"));
            patient.setFullName(rs.getString("full_name"));
            patient.setDateOfBirth(rs.getDate("DateOfBirth") != null ? 
                rs.getDate("DateOfBirth").toLocalDate() : null);
            patient.setAge(rs.getInt("age"));
            patient.setPhone(rs.getString("phone"));
            patient.setEmail(rs.getString("email"));
            patient.setGender(rs.getString("Gender"));
            patient.setStatus(rs.getString("status"));
            patient.setDonationDate(rs.getDate("donation_date") != null ? 
                rs.getDate("donation_date").toLocalDate() : null);
            return patient;
        }
    }

    public List<Patient> findAll() {
        String sql = "SELECT * FROM Patients ORDER BY patient_id";
        return jdbcTemplate.query(sql, new PatientRowMapper());
    }

    public Patient findById(String id) {
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";
        List<Patient> patients = jdbcTemplate.query(sql, new PatientRowMapper(), id);
        return patients.isEmpty() ? null : patients.get(0);
    }

    public List<Patient> searchByName(String name) {
        String sql = "SELECT * FROM Patients WHERE full_name LIKE ? ORDER BY full_name";
        return jdbcTemplate.query(sql, new PatientRowMapper(), "%" + name + "%");
    }

    public List<Patient> findByGender(String gender) {
        String sql = "SELECT * FROM Patients WHERE Gender = ? ORDER BY patient_id";
        return jdbcTemplate.query(sql, new PatientRowMapper(), gender);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Patients";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

