package com.blooddonation.dao;

import com.blooddonation.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * RequestDAO - Data Access Object for Request entity
 */
@Repository
public class RequestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class RequestRowMapper implements RowMapper<Request> {
        @Override
        public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
            Request request = new Request();
            request.setRequestId(rs.getString("request_id"));
            request.setBankId(rs.getInt("bank_id"));
            request.setBloodType(rs.getString("blood_type"));
            request.setQuantity(rs.getInt("quantity"));
            request.setStatus(rs.getString("status"));
            request.setDateRequest(rs.getDate("date_request") != null ?
                rs.getDate("date_request").toLocalDate() : null);
            request.setDateResponse(rs.getTimestamp("date_response") != null ?
                rs.getTimestamp("date_response").toLocalDateTime() : null);
            request.setFulfilledInventoryId(rs.getObject("fulfilled_inventory_id", Integer.class));
            request.setPatientId(rs.getString("patient_id"));
            request.setDoctorId(rs.getString("doctor_id"));
            request.setDonorsId(rs.getString("donors_id"));
            return request;
        }
    }

    public List<Request> findAll() {
        String sql = "SELECT * FROM Request ORDER BY request_id";
        return jdbcTemplate.query(sql, new RequestRowMapper());
    }

    public Request findById(String id) {
        String sql = "SELECT * FROM Request WHERE request_id = ?";
        List<Request> requests = jdbcTemplate.query(sql, new RequestRowMapper(), id);
        return requests.isEmpty() ? null : requests.get(0);
    }

    public List<Request> findByStatus(String status) {
        String sql = "SELECT * FROM Request WHERE status = ? ORDER BY request_id";
        return jdbcTemplate.query(sql, new RequestRowMapper(), status);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Request";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public int countByStatus(String status) {
        String sql = "SELECT COUNT(*) FROM Request WHERE status = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, status);
    }

    public List<Request> findRecent(int limit) {
        String sql = "SELECT * FROM Request ORDER BY date_request DESC LIMIT ?";
        return jdbcTemplate.query(sql, new RequestRowMapper(), limit);
    }

    public List<Request> findByPatientId(String patientId) {
        String sql = "SELECT * FROM Request WHERE patient_id = ? ORDER BY date_request DESC";
        return jdbcTemplate.query(sql, new RequestRowMapper(), patientId);
    }
}



