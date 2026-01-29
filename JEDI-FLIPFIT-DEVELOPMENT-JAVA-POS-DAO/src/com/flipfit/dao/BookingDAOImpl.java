package com.flipfit.dao;

import com.flipfit.bean.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {
    private final DBConnectionManager connectionManager = new DBConnectionManager();

    @Override
    public void save(Booking booking) {
        String sql = "INSERT INTO bookings (booking_id, user_id, slot_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, booking.getBookingId());
            ps.setString(2, booking.getUserId());
            ps.setString(3, booking.getSlotId());
            ps.setString(4, booking.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save booking", e);
        }
    }

    @Override
    public boolean delete(String bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to delete booking", e);
        }
    }

    @Override
    public List<Booking> findByUser(String userId) {
        String sql = "SELECT booking_id, slot_id, status FROM bookings WHERE user_id = ?";
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setBookingId(rs.getString("booking_id"));
                    booking.setUserId(userId);
                    booking.setSlotId(rs.getString("slot_id"));
                    booking.setStatus(rs.getString("status"));
                    bookings.add(booking);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch bookings", e);
        }
        return bookings;
    }
}
