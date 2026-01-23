package com.flipfit.service;

import com.flipfit.core.User;
import com.flipfit.core.Role;
import com.flipfit.core.Admin;
import com.flipfit.core.GymMember;
import com.flipfit.core.GymOwner;
import com.flipfit.db.UserDAO;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Optional<User> registerUser(String name, String email, String password, Role role) {
        // In a real application, password should be hashed and salted
        String id = UUID.randomUUID().toString();
        User user;
        switch (role) {
            case ADMIN:
                user = new Admin(id, name, email, password);
                break;
            case GYM_MEMBER:
                user = new GymMember(id, name, email, password, null); // Credit card handled separately or upon first booking
                break;
            case GYM_OWNER:
                user = new GymOwner(id, name, email, password, com.flipfit.core.Status.PENDING); // Default PENDING status
                break;
            default:
                return Optional.empty(); // Should not happen if Role enum is exhaustive
        }

        try {
            userDAO.insert(user);
            return Optional.of(user);
        } catch (Exception e) {
            // Log the exception (e.g., duplicate email)
            return Optional.empty();
        }
    }

    public Optional<User> findUserById(String id) {
        return userDAO.findById(id);
    }

    public Optional<User> findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(String id) {
        userDAO.deleteById(id);
    }
}
