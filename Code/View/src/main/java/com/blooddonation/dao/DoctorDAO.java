package com.blooddonation.dao;

import com.blooddonation.model.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DoctorDAO - Data Access Object for Doctor entity
 */
@Repository
public class DoctorDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class DoctorRowMapper implements RowMapper<Doctor> {
        @Override
        public Doctor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Doctor doctor = new Doctor();
            doctor.setDoctorId(rs.getString("doctor_id"));
            doctor.setSsn(rs.getString("ssn"));
            doctor.setFullName(rs.getString("full_name"));
            doctor.setEmail(rs.getString("email"));
            doctor.setPhone(rs.getString("phone"));
            doctor.setSpecialization(rs.getString("specialization"));
            return doctor;
        }
    }

    public List<Doctor> findAll() {
        String sql = "SELECT * FROM Doctor ORDER BY doctor_id";
        return jdbcTemplate.query(sql, new DoctorRowMapper());
    }

    public Doctor findById(String id) {
        String sql = "SELECT * FROM Doctor WHERE doctor_id = ?";
        List<Doctor> doctors = jdbcTemplate.query(sql, new DoctorRowMapper(), id);
        return doctors.isEmpty() ? null : doctors.get(0);
    }

    public List<Doctor> searchBySpecialty(String specialty) {
        String sql = "SELECT * FROM Doctor WHERE specialization LIKE ? ORDER BY full_name";
        return jdbcTemplate.query(sql, new DoctorRowMapper(), "%" + specialty + "%");
    }

    public List<Doctor> searchByName(String name) {
        String sql = "SELECT * FROM Doctor WHERE full_name LIKE ? ORDER BY full_name";
        return jdbcTemplate.query(sql, new DoctorRowMapper(), "%" + name + "%");
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Doctor";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}

