package com.flipfit.bean;

import java.util.ArrayList;
import java.util.List;

public class FlipFitMockDB {
    // These static lists act as our database tables
    public static List<User> users = new ArrayList<>();
    public static List<GymCentre> gymCentres = new ArrayList<>();
    public static List<Slots> slots = new ArrayList<>();
    public static List<Booking> bookings = new ArrayList<>();

    // Static block to add a default Admin for testing
    static {
        User admin = new User("admin", "admin", "admin@flipfit.com", "admin123", new Role("1", "Admin", "Super User"));
        users.add(admin);
    }
}