package com.flipfit.bean;

/**
 * Domain model representing a FlipFit platform user.
 *
 * <p>A user encapsulates credentials, contact information, and the
 * associated {@link Role} that drives authorization within the system.</p>
 *
 * @author diya-kailash
 */
public class User {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private Role role; // <--- CHANGED: Now it is an Object of type Role

    public User() {}

    public User(String userId, String userName, String email, String password, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Updated Getter/Setter for Role
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}