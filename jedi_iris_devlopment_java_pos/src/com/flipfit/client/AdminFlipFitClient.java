package com.flipfit.client;

import com.flipfit.business.AdminFlipFitService;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCentre;
import java.util.List;
import java.util.Scanner;

public class AdminFlipFitClient {

    AdminFlipFitService adminService = new AdminFlipFitService();

    public void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. View Pending Gym Owners");
            System.out.println("2. Approve Gym Owner");
            System.out.println("3. View Pending Gym Centres");
            System.out.println("4. Approve Gym Centre");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    List<GymOwner> pendingOwners = adminService.getPendingOwners();
                    // In a real app, you would loop and print these
                    System.out.println("List of pending owners displayed here...");
                    break;
                case 2:
                    System.out.print("Enter Owner ID to approve: ");
                    String ownerId = scanner.next();
                    adminService.approveGymOwner(ownerId, true);
                    break;
                case 3:
                    List<GymCentre> pendingCentres = adminService.getPendingCentres();
                    System.out.println("List of pending centres displayed here...");
                    break;
                case 4:
                    System.out.print("Enter Centre ID to approve: ");
                    String centreId = scanner.next();
                    adminService.approveGymCentre(centreId, true);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return; // Returns to Main Menu
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}