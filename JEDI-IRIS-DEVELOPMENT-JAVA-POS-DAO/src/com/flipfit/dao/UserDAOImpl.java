package com.flipfit.dao;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.Role;
import com.flipfit.bean.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {
    private final DBConnectionManager connectionManager = new DBConnectionManager();

    @Override
    public Optional<User> findByCredentials(String userName, String password) {
        String sql = "SELECT user_id, user_name, email, password, role_name, pan_number, card_details, is_approved FROM users WHERE user_name = ? AND password = ? LIMIT 1";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String roleName = rs.getString("role_name");
                    boolean approved = rs.getBoolean("is_approved");
                    if ("GymOwner".equalsIgnoreCase(roleName)) {
                        GymOwner owner = new GymOwner();
                        owner.setUserId(rs.getString("user_id"));
                        owner.setUserName(rs.getString("user_name"));
                        owner.setEmail(rs.getString("email"));
                        owner.setPassword(rs.getString("password"));
                        owner.setApproved(approved);
                        owner.setPanNumber(rs.getString("pan_number"));
                        owner.setCardDetails(rs.getString("card_details"));
                        return Optional.of(owner);
                    }
                    User user = new User();
                    user.setUserId(rs.getString("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(new Role("", roleName, ""));
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to query user", e);
        }
        return Optional.empty();
    }

    @Override
    public void saveCustomer(GymCustomer gymCustomer) {
        String sql = "INSERT INTO users (user_id, user_name, email, password, role_name, is_approved) VALUES (?, ?, ?, ?, 'GymCustomer', 1)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gymCustomer.getUserId());
            ps.setString(2, gymCustomer.getUserName());
            ps.setString(3, gymCustomer.getEmail());
            ps.setString(4, gymCustomer.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save customer", e);
        }
    }

    @Override
    public void saveOwner(GymOwner gymOwner) {
        String sql = "INSERT INTO users (user_id, user_name, email, password, role_name, pan_number, card_details, is_approved) VALUES (?, ?, ?, ?, 'GymOwner', ?, ?, 0)";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gymOwner.getUserId());
            ps.setString(2, gymOwner.getUserName());
            ps.setString(3, gymOwner.getEmail());
            ps.setString(4, gymOwner.getPassword());
            ps.setString(5, gymOwner.getPanNumber());
            ps.setString(6, gymOwner.getCardDetails());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to save gym owner", e);
        }
    }

    @Override
    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE user_name = ? AND password = ?";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setString(2, userName);
            ps.setString(3, oldPassword);
            int updated = ps.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to change password", e);
        }
    }

    @Override
    public boolean updateOwnerApproval(String ownerUserName, boolean isApproved) {
        String sql = "UPDATE users SET is_approved = ? WHERE user_name = ? AND role_name = 'GymOwner'";
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, isApproved);
            ps.setString(2, ownerUserName);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to update owner approval", e);
        }
    }

    @Override
    public List<GymOwner> findPendingOwners() {
        String sql = "SELECT user_id, user_name, email, password, pan_number, card_details FROM users WHERE role_name = 'GymOwner' AND is_approved = 0";
        List<GymOwner> pending = new ArrayList<>();
        try (Connection conn = connectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GymOwner owner = new GymOwner();
                owner.setUserId(rs.getString("user_id"));
                owner.setUserName(rs.getString("user_name"));
                owner.setEmail(rs.getString("email"));
                owner.setPassword(rs.getString("password"));
                owner.setPanNumber(rs.getString("pan_number"));
                owner.setCardDetails(rs.getString("card_details"));
                owner.setApproved(false);
                pending.add(owner);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to fetch pending owners", e);
        }
        return pending;
    }
}
