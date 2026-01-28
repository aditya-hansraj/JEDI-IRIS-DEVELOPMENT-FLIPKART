package com.flipfit.dao;

import com.flipfit.bean.Slots;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Slots findById(String slotId) {
        String sql = "SELECT slot_id, centre_id, start_time, total_seats, available_seats FROM slots WHERE slot_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slotId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Slots slot = new Slots();
                slot.setSlotId(rs.getString("slot_id"));
                slot.setCentreId(rs.getString("centre_id"));
                slot.setStartTime(rs.getString("start_time"));
                slot.setTotalSeats(rs.getInt("total_seats"));
                slot.setAvailableSeats(rs.getInt("available_seats"));
                return slot;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch slot", e);
        }
    }

    @Override
    public boolean reserveSeat(String slotId) {
        String sql = "UPDATE slots SET available_seats = available_seats - 1 WHERE slot_id = ? AND available_seats > 0";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slotId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to reserve seat for slot " + slotId, e);
        }
    }

    @Override
    public void releaseSeat(String slotId) {
        String sql = "UPDATE slots SET available_seats = available_seats + 1 WHERE slot_id = ? AND available_seats < total_seats";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, slotId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to release seat for slot " + slotId, e);
        }
    }

    @Override
    public List<Slots> findByCentre(String centreId) {
        String sql = "SELECT slot_id, centre_id, start_time, total_seats, available_seats FROM slots WHERE centre_id = ?";
        List<Slots> slots = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, centreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Slots slot = new Slots();
                    slot.setSlotId(rs.getString("slot_id"));
                    slot.setCentreId(rs.getString("centre_id"));
                    slot.setStartTime(rs.getString("start_time"));
                    slot.setTotalSeats(rs.getInt("total_seats"));
                    slot.setAvailableSeats(rs.getInt("available_seats"));
                    slots.add(slot);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch slots for centre " + centreId, e);
        }
        return slots;
    }
}
