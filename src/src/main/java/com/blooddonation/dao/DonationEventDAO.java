package com.blooddonation.dao;

import com.blooddonation.model.DonationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * DonationEventDAO - Data Access Object for DonationEvent entity
 */
@Repository
public class DonationEventDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static class DonationEventRowMapper implements RowMapper<DonationEvent> {
        @Override
        public DonationEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
            DonationEvent event = new DonationEvent();
            event.setDonationId(rs.getString("donation_id"));
            event.setDateEvent(rs.getDate("Date_event") != null ? 
                rs.getDate("Date_event").toLocalDate() : null);
            event.setLocation(rs.getString("location"));
            event.setVolumeCollected(rs.getBigDecimal("volume_collected"));
            event.setBankId(rs.getString("bank_id"));
            return event;
        }
    }

    public List<DonationEvent> findAll() {
        String sql = "SELECT * FROM Donation_Event ORDER BY donation_id";
        return jdbcTemplate.query(sql, new DonationEventRowMapper());
    }

    public DonationEvent findById(String id) {
        String sql = "SELECT * FROM Donation_Event WHERE donation_id = ?";
        List<DonationEvent> events = jdbcTemplate.query(sql, new DonationEventRowMapper(), id);
        return events.isEmpty() ? null : events.get(0);
    }

    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Donation_Event";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<DonationEvent> findByDonorId(String donorId) {
        String sql = "SELECT de.* FROM Donation_Event de " +
                     "INNER JOIN Donors_DonationEvent dde ON de.donation_id = dde.donation_id " +
                     "WHERE dde.donors_id = ? " +
                     "ORDER BY de.Date_event DESC";
        return jdbcTemplate.query(sql, new DonationEventRowMapper(), donorId);
    }
}

