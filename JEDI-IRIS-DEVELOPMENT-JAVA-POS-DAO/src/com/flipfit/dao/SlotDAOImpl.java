package com.flipfit.dao;

import com.flipfit.bean.Slots;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SlotDAOImpl implements SlotDAO {
    private final DBConnectionManager connectionManager = new DBConnectionManager();

    @Override
    public void save(Slots slot) {
        String sql = "INSERT INTO slots (slot_id, centre_id, start_time, total_seats, available_seats) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slot.getSlotId());
            ps.setString(2, slot.getCentreId());
            ps.setString(3, slot.getStartTime());
            ps.setInt(4, slot.getTotalSeats());
            ps.setInt(5, slot.getAvailableSeats());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save slot", e);
        }
    }
}
