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
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. View Pending Gym Owners");
            System.out.println("2. Approve Gym Owner");
            System.out.println("3. View Pending Gym Centres");
            System.out.println("4. Approve Gym Centre");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- PENDING OWNERS ---");
                    List<GymOwner> pendingOwners = adminService.getPendingOwners();
                    if (pendingOwners.isEmpty()) {
                        System.out.println("No pending approvals.");
                    } else {
                        // Clean Display
                        System.out.printf("%-15s %-25s%n", "USERNAME", "EMAIL");
                        System.out.println("------------------------------------------");
                        for (GymOwner o : pendingOwners) {
                            System.out.printf("%-15s %-25s%n", o.getUserName(), o.getEmail());
                        }
                    }
                    break;

                case 2:
                    System.out.print("\nEnter Gym Owner Username to Approve: ");
                    String ownerName = scanner.next();
                    adminService.approveGymOwner(ownerName, true);
                    break;

                case 3:
                    System.out.println("\n--- PENDING CENTRES ---");
                    List<GymCentre> pendingCentres = adminService.getPendingCentres();
                    if (pendingCentres.isEmpty()) {
                        System.out.println("No pending centres.");
                    } else {
                        // Clean Display
                        System.out.printf("%-10s %-20s %-15s%n", "CENTRE ID", "NAME", "CITY");
                        System.out.println("--------------------------------------------------");
                        for (GymCentre c : pendingCentres) {
                            System.out.printf("%-10s %-20s %-15s%n", c.getCentreId(), c.getCentreName(), c.getCity());
                        }
                    }
                    break;

                case 4:
                    System.out.print("\nEnter Centre ID to Approve: ");
                    String centreId = scanner.next();
                    adminService.approveGymCentre(centreId, true);
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}