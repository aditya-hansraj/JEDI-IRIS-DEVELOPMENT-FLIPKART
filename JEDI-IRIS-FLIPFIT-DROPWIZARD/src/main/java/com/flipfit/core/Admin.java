package com.flipfit.core;

// Admin extends User but doesn't have any additional specific attributes in the diagram.
// It mainly has methods, which would be implemented in a service or resource layer.
public class Admin extends User {
    public Admin() {
        super();
        this.setRole(Role.ADMIN);
    }

    public Admin(String id, String name, String email, String password) {
        super(id, name, email, password, Role.ADMIN);
    }
}
