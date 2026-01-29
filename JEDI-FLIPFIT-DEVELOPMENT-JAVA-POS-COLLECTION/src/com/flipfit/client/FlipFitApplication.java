package com.flipfit.client;

import com.flipfit.business.UserFlipFitService;
import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import java.util.Scanner;

public class FlipFitApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserFlipFitService userService = new UserFlipFitService();

        // Instantiate the sub-clients
        AdminFlipFitClient adminClient = new AdminFlipFitClient();
        GymCustomerFlipFitClient customerClient = new GymCustomerFlipFitClient();
        GymOwnerFlipFitClient ownerClient = new GymOwnerFlipFitClient();

        while (true) {
            System.out.println("\n=== WELCOME TO THE FLIPFIT APPLICATION FOR GYM ===");
            System.out.println("1. Login");
            System.out.println("2. Register as Customer");
            System.out.println("3. Register as Gym Owner");
            System.out.println("4. Change Password");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("--- LOGIN ---");
                    System.out.print("Enter Username: ");
                    String user = scanner.next();  // <--- You capture the username here
                    System.out.print("Enter Password: ");
                    String pass = scanner.next();
                    System.out.print("Enter Role (Admin/Customer/GymOwner): ");
                    String role = scanner.next();

                    if (userService.login(user, pass)) {
                        if (role.equalsIgnoreCase("Admin")) {
                            adminClient.adminMenu(scanner);
                        } else if (role.equalsIgnoreCase("Customer")) {
                            customerClient.customerMenu(scanner, user);
                        } else if (role.equalsIgnoreCase("GymOwner")) {
                            ownerClient.gymOwnerMenu(scanner, user);
                            // -------------------
                        } else {
                            System.out.println("Invalid Role Selected.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("--- CUSTOMER REGISTRATION ---");
                    System.out.print("Enter Name: ");
                    String custName = scanner.next();
                    System.out.print("Enter Password: ");
                    String custPass = scanner.next();
                    System.out.print("Enter Email: ");
                    String custEmail = scanner.next();

                    GymCustomer newCust = new GymCustomer();
                    newCust.setUserName(custName);
                    newCust.setPassword(custPass);
                    newCust.setEmail(custEmail);

                    userService.registerCustomer(newCust);
                    break;

                case 3:
                    System.out.println("--- GYM OWNER REGISTRATION ---");
                    System.out.print("Enter Name: ");
                    String ownerName = scanner.next();
                    System.out.print("Enter Password: ");
                    String ownerPass = scanner.next();
                    System.out.print("Enter Email: ");
                    String ownerEmail = scanner.next();

                    GymOwner newOwner = new GymOwner();
                    newOwner.setUserName(ownerName);
                    newOwner.setPassword(ownerPass);
                    newOwner.setEmail(ownerEmail);

                    userService.registerGymOwner(newOwner);
                    break;

                case 4:
                    System.out.println("--- CHANGE PASSWORD ---");
                    System.out.print("Enter Username: ");
                    String userName = scanner.next();
                    System.out.print("Enter Old Password: ");
                    String oldPass = scanner.next();
                    System.out.print("Enter New Password: ");
                    String newPass = scanner.next();
                    
                    boolean isChanged = userService.changePassword(userName, oldPass, newPass);
                    if (isChanged) {
                        System.out.println("Password changed successfully!");
                    } else {
                        System.out.println("Failed to change password. Please check your credentials.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting Application. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}