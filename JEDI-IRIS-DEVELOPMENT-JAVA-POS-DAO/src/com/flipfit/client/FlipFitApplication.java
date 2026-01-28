package com.flipfit.client;

import com.flipfit.bean.GymCustomer;
import com.flipfit.bean.GymOwner;
import com.flipfit.bean.User;
import com.flipfit.business.UserFlipFitService;
import com.flipfit.exception.ApprovalPendingException;
import com.flipfit.exception.RegistrationNotDoneException;
import com.flipfit.exception.UserNotFoundException;
import java.util.Scanner;

/**
 * Entry point for the FlipFit gym management console application.
 *
 * <p>This class presents the top-level menu for users to log in,
 * register as customers or gym owners, and manage credentials.</p>
 *
 * @author aditya-hansraj
 */
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
                    try {
                        User loggedInUser = userService.login(user, pass);
                        String resolvedRole = resolveRole(loggedInUser);

                        if ("Admin".equalsIgnoreCase(resolvedRole)) {
                            adminClient.adminMenu(scanner);
                        } else if ("GymCustomer".equalsIgnoreCase(resolvedRole) || "Customer".equalsIgnoreCase(resolvedRole)) {
                            customerClient.customerMenu(scanner, loggedInUser.getUserName());
                        } else if ("GymOwner".equalsIgnoreCase(resolvedRole)) {
                            ownerClient.gymOwnerMenu(scanner, loggedInUser.getUserName());
                        } else {
                            System.out.println("Unable to determine role for the user. Please contact support.");
                        }
                    } catch (UserNotFoundException | ApprovalPendingException e) {
                        System.out.println(e.getMessage());
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

                    try {
                        userService.registerCustomer(newCust);
                    } catch (RegistrationNotDoneException e) {
                        System.out.println(e.getMessage());
                    }
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

                    try {
                        userService.registerGymOwner(newOwner);
                    } catch (RegistrationNotDoneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("--- CHANGE PASSWORD ---");
                    System.out.print("Enter Username: ");
                    String userName = scanner.next();
                    System.out.print("Enter Old Password: ");
                    String oldPass = scanner.next();
                    System.out.print("Enter New Password: ");
                    String newPass = scanner.next();
                    
                    try {
                        boolean isChanged = userService.changePassword(userName, oldPass, newPass);
                        if (isChanged) {
                            System.out.println("Password changed successfully!");
                        }
                    } catch (UserNotFoundException e) {
                        System.out.println(e.getMessage());
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

    private static String resolveRole(User user) {
        if (user == null) {
            return "";
        }
        if (user instanceof GymOwner) {
            return "GymOwner";
        }
        if (user instanceof GymCustomer) {
            return "GymCustomer";
        }
        if (user.getRole() != null && user.getRole().getRoleName() != null) {
            return user.getRole().getRoleName();
        }
        return "";
    }
}