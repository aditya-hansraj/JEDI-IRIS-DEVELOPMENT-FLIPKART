package com.flipfit.dao;

import com.flipfit.bean.GymCentre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GymCentreDAOImpl implements GymCentreDAO {
    private final DBConnectionManager connectionManager = new DBConnectionManager();

    @Override
    public void save(GymCentre centre) {
        String sql = "INSERT INTO gym_centres (centre_id, owner_id, centre_name, city, pincode, is_approved) VALUES (?, ?, ?, ?, ?, 0)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, centre.getCentreId());
            ps.setString(2, centre.getOwnerId());
            ps.setString(3, centre.getCentreName());
            ps.setString(4, centre.getCity());
            ps.setString(5, centre.getPincode());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save gym centre", e);
        }
    }

    @Override
    public boolean updateApproval(String centreId, boolean isApproved) {
        String sql = "UPDATE gym_centres SET is_approved = ? WHERE centre_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isApproved);
            ps.setString(2, centreId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update centre approval", e);
        }
    }

    @Override
    public List<GymCentre> findPendingCentres() {
        String sql = "SELECT centre_id, owner_id, centre_name, city, pincode FROM gym_centres WHERE is_approved = 0";
        List<GymCentre> pending = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GymCentre centre = new GymCentre();
                centre.setCentreId(rs.getString("centre_id"));
                centre.setOwnerId(rs.getString("owner_id"));
                centre.setCentreName(rs.getString("centre_name"));
                centre.setCity(rs.getString("city"));
                centre.setPincode(rs.getString("pincode"));
                centre.setApproved(false);
                pending.add(centre);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch pending centres", e);
        }
        return pending;
    }

    @Override
    public List<GymCentre> findByOwner(String ownerId) {
        String sql = "SELECT centre_id, centre_name, city, pincode, is_approved FROM gym_centres WHERE owner_id = ?";
        List<GymCentre> centres = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ownerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GymCentre centre = new GymCentre();
                    centre.setCentreId(rs.getString("centre_id"));
                    centre.setOwnerId(ownerId);
                    centre.setCentreName(rs.getString("centre_name"));
                    centre.setCity(rs.getString("city"));
                    centre.setPincode(rs.getString("pincode"));
                    centre.setApproved(rs.getBoolean("is_approved"));
                    centres.add(centre);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch centres by owner", e);
        }
        return centres;
    }

    @Override
    public List<GymCentre> findApprovedCentres() {
        String sql = "SELECT centre_id, owner_id, centre_name, city, pincode FROM gym_centres WHERE is_approved = 1";
        List<GymCentre> centres = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GymCentre centre = new GymCentre();
                centre.setCentreId(rs.getString("centre_id"));
                centre.setOwnerId(rs.getString("owner_id"));
                centre.setCentreName(rs.getString("centre_name"));
                centre.setCity(rs.getString("city"));
                centre.setPincode(rs.getString("pincode"));
                centre.setApproved(true);
                centres.add(centre);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch approved centres", e);
        }
        return centres;
    }

    @Override
    public GymCentre findById(String centreId) {
        String sql = "SELECT centre_id, owner_id, centre_name, city, pincode, is_approved FROM gym_centres WHERE centre_id = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, centreId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                GymCentre centre = new GymCentre();
                centre.setCentreId(rs.getString("centre_id"));
                centre.setOwnerId(rs.getString("owner_id"));
                centre.setCentreName(rs.getString("centre_name"));
                centre.setCity(rs.getString("city"));
                centre.setPincode(rs.getString("pincode"));
                centre.setApproved(rs.getBoolean("is_approved"));
                return centre;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch centre details", e);
        }
    }

    @Override
    public List<GymCentre> findAllCentres() {
        String sql = "SELECT centre_id, owner_id, centre_name, city, pincode, is_approved FROM gym_centres";
        List<GymCentre> centres = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GymCentre centre = new GymCentre();
                centre.setCentreId(rs.getString("centre_id"));
                centre.setOwnerId(rs.getString("owner_id"));
                centre.setCentreName(rs.getString("centre_name"));
                centre.setCity(rs.getString("city"));
                centre.setPincode(rs.getString("pincode"));
                centre.setApproved(rs.getBoolean("is_approved"));
                centres.add(centre);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch all centres", e);
        }
        return centres;
    }
}
