package com.flipfit.client;

import com.flipfit.business.GymCustomerFlipFitService;
import java.util.Scanner;

public class GymCustomerFlipFitClient {

    GymCustomerFlipFitService customerService = new GymCustomerFlipFitService();

    // CHANGE 1: Added 'String userId' to the parameters here
    public void customerMenu(Scanner scanner, String userId) {
        while (true) {
            System.out.println("\n--- CUSTOMER MENU (" + userId + ") ---"); // Shows who is logged in
            System.out.println("1. View My Bookings");
            System.out.println("2. Book a Slot");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // CHANGE 2: Passed the real userId
                    customerService.getMyBookings(userId);
                    break;
                case 2:
                    System.out.print("Enter Slot ID: ");
                    String slotId = scanner.next();
                    // CHANGE 3: Passed the real userId
                    customerService.bookSlot("NewBookingId", slotId, userId);
                    break;
                case 3:
                    System.out.print("Enter Booking ID: ");
                    String bookingId = scanner.next();
                    customerService.cancelBooking(bookingId);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}