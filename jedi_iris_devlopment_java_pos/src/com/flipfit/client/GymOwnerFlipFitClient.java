package com.flipfit.client;

import com.flipfit.business.GymOwnerFlipFitService;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import java.util.Scanner;

public class GymOwnerFlipFitClient {

    GymOwnerFlipFitService ownerService = new GymOwnerFlipFitService();

    public void gymOwnerMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n--- GYM OWNER MENU ---");
            System.out.println("1. View My Centres");
            System.out.println("2. Add New Centre");
            System.out.println("3. Add Slots to Centre");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // ownerId would normally come from the Login session
                    ownerService.viewMyCentres("dummyOwnerId");
                    break;
                case 2:
                    System.out.print("Enter Centre Name: ");
                    String name = scanner.next();
                    System.out.print("Enter City: ");
                    String city = scanner.next();

                    GymCentre newCentre = new GymCentre();
                    newCentre.setCentreName(name);
                    newCentre.setCity(city);
                    newCentre.setOwnerId("dummyOwnerId");

                    ownerService.addCentre(newCentre);
                    break;
                case 3:
                    System.out.print("Enter Centre ID: ");
                    String centreId = scanner.next();
                    System.out.print("Enter Slot Time: ");
                    String time = scanner.next();

                    Slots newSlot = new Slots();
                    newSlot.setCentreId(centreId);
                    newSlot.setStartTime(time);

                    ownerService.addSlot(newSlot);
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