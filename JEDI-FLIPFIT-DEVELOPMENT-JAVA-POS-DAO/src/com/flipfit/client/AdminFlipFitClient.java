package com.flipfit.client;

import com.flipfit.business.AdminFlipFitService;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.GymCentre;
import com.flipfit.exception.ApprovalNotDoneException;
import java.util.List;
import java.util.Scanner;

/**
 * Console client for administrative workflows.
 *
 * <p>Presents a menu for admins to view and approve pending gym
 * owners and centres via {@link AdminFlipFitService}.</p>
 *
 * @author aditya-hansraj
 */
public class AdminFlipFitClient {

    AdminFlipFitService adminService = new AdminFlipFitService();

    public void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- ADMIN DASHBOARD ---");
            System.out.println("1. View Pending Gym Owners");
            System.out.println("2. Approve Gym Owner");
            System.out.println("3. View Pending Gym Centres");
            System.out.println("4. Approve Gym Centre");
            System.out.println("5. View Owners by Approval");
            System.out.println("6. View Centres by Approval");
            System.out.println("7. Logout");
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
                    try {
                        adminService.approveGymOwner(ownerName, true);
                    } catch (ApprovalNotDoneException e) {
                        System.out.println(e.getMessage());
                    }
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
                    try {
                        adminService.approveGymCentre(centreId, true);
                    } catch (ApprovalNotDoneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("\n--- OWNERS BY APPROVAL ---");
                    List<GymOwner> approvedOwners = adminService.getApprovedOwners();
                    List<GymOwner> notApprovedOwners = adminService.getNotApprovedOwners();

                    System.out.println("APPROVED OWNERS:");
                    if (approvedOwners.isEmpty()) {
                        System.out.println("None");
                    } else {
                        System.out.printf("%-15s %-25s%n", "USERNAME", "EMAIL");
                        System.out.println("------------------------------------------");
                        for (GymOwner o : approvedOwners) {
                            System.out.printf("%-15s %-25s%n", o.getUserName(), o.getEmail());
                        }
                    }

                    System.out.println("\nNOT APPROVED OWNERS:");
                    if (notApprovedOwners.isEmpty()) {
                        System.out.println("None");
                    } else {
                        System.out.printf("%-15s %-25s%n", "USERNAME", "EMAIL");
                        System.out.println("------------------------------------------");
                        for (GymOwner o : notApprovedOwners) {
                            System.out.printf("%-15s %-25s%n", o.getUserName(), o.getEmail());
                        }
                    }
                    break;

                case 6:
                    System.out.println("\n--- CENTRES BY APPROVAL ---");
                    List<GymCentre> approvedCentres = adminService.getApprovedCentres();
                    List<GymCentre> notApprovedCentres = adminService.getNotApprovedCentres();

                    System.out.println("APPROVED CENTRES:");
                    if (approvedCentres.isEmpty()) {
                        System.out.println("None");
                    } else {
                        System.out.printf("%-10s %-20s %-15s%n", "CENTRE ID", "NAME", "CITY");
                        System.out.println("--------------------------------------------------");
                        for (GymCentre c : approvedCentres) {
                            System.out.printf("%-10s %-20s %-15s%n", c.getCentreId(), c.getCentreName(), c.getCity());
                        }
                    }

                    System.out.println("\nNOT APPROVED CENTRES:");
                    if (notApprovedCentres.isEmpty()) {
                        System.out.println("None");
                    } else {
                        System.out.printf("%-10s %-20s %-15s%n", "CENTRE ID", "NAME", "CITY");
                        System.out.println("--------------------------------------------------");
                        for (GymCentre c : notApprovedCentres) {
                            System.out.printf("%-10s %-20s %-15s%n", c.getCentreId(), c.getCentreName(), c.getCity());
                        }
                    }
                    break;

                case 7:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}