package com.flipfit.client;

import com.flipfit.business.GymOwnerFlipFitService;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.exception.RegistrationNotDoneException;
import java.util.Scanner;
import java.util.UUID;

// TODO: Auto-generated Javadoc
/**
 * The Class GymOwnerFlipFitClient.
 *
 * @author AI
 * @ClassName "GymOwnerFlipFitClient"
 */
public class GymOwnerFlipFitClient {

    GymOwnerFlipFitService ownerService = new GymOwnerFlipFitService();

    // UPDATED: Now accepts ownerId to associate centre with the logged-in user
    public void gymOwnerMenu(Scanner scanner, String ownerId) {
        while (true) {
            System.out.println("\n--- GYM OWNER MENU (" + ownerId + ") ---");
            System.out.println("1. View My Centres");
            System.out.println("2. Add New Centre");
            System.out.println("3. Add Slots to Centre");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline left after nextInt

            switch (choice) {
                case 1:
                    ownerService.viewMyCentres(ownerId);
                    break;
                case 2:
                    System.out.print("Enter Centre Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter City: ");
                    String city = scanner.nextLine();
                    System.out.print("Enter Pincode: ");
                    String pincode = scanner.nextLine();

                    GymCentre newCentre = new GymCentre();
                    newCentre.setCentreId(UUID.randomUUID().toString().substring(0, 8)); // Generate random ID
                    newCentre.setCentreName(name);
                    newCentre.setCity(city);
                    newCentre.setPincode(pincode);
                    newCentre.setOwnerId(ownerId);
                    newCentre.setApproved(false);

                    try {
                        ownerService.addCentre(newCentre);
                    } catch (RegistrationNotDoneException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Enter Centre ID: ");
                    String centreId = scanner.nextLine();
                    System.out.print("Enter Slot Time: ");
                    String time = scanner.nextLine();

                    Slots newSlot = new Slots();
                    newSlot.setSlotId(UUID.randomUUID().toString().substring(0, 8));
                    newSlot.setCentreId(centreId);
                    newSlot.setStartTime(time);

                    try {
                        ownerService.addSlot(newSlot);
                    } catch (RegistrationNotDoneException e) {
                        System.out.println(e.getMessage());
                    }
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