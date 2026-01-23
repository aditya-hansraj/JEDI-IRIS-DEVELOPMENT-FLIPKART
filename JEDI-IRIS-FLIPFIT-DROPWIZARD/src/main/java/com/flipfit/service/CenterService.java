package com.flipfit.service;

import com.flipfit.core.Center;
import com.flipfit.core.Status;
import com.flipfit.db.CenterDAO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CenterService {
    private final CenterDAO centerDAO;

    public CenterService(CenterDAO centerDAO) {
        this.centerDAO = centerDAO;
    }

    public Optional<Center> createCenter(String name, String address, String gymOwnerId) {
        String id = UUID.randomUUID().toString();
        Center center = new Center(id, name, address, gymOwnerId, Status.PENDING); // New centers are PENDING by default
        try {
            centerDAO.insert(center);
            return Optional.of(center);
        } catch (Exception e) {
            // Log exception
            return Optional.empty();
        }
    }

    public Optional<Center> getCenterById(String id) {
        return centerDAO.findById(id);
    }

    public List<Center> getCentersByGymOwnerId(String gymOwnerId) {
        return centerDAO.findByGymOwnerId(gymOwnerId);
    }

    public List<Center> getAllCenters() {
        return centerDAO.findAll();
    }

    public void updateCenter(Center center) {
        centerDAO.update(center);
    }

    public void deleteCenter(String id) {
        centerDAO.deleteById(id);
    }

    public Optional<Center> approveCenter(String centerId) {
        return centerDAO.findById(centerId).map(center -> {
            center.setApprovalStatus(Status.APPROVED);
            centerDAO.update(center);
            return center;
        });
    }

    public Optional<Center> rejectCenter(String centerId) {
        return centerDAO.findById(centerId).map(center -> {
            center.setApprovalStatus(Status.REJECTED);
            centerDAO.update(center);
            return center;
        });
    }
}
