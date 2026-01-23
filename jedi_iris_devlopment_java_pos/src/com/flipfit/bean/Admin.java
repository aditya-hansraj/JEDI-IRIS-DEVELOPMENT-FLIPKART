package com.flipfit.bean;

public class Admin extends User {

    public Admin() {
        // Set Role Object
        setRole(new Role("3", "Admin", "System Administrator"));
    }
}