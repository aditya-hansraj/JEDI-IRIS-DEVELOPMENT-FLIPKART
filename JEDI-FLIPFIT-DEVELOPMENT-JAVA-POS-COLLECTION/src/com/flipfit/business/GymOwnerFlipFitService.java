package com.flipfit.business;

import com.flipfit.bean.FlipFitMockDB;
import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import java.util.List;
import java.util.stream.Collectors;

public class GymOwnerFlipFitService implements GymOwnerFlipFitInterface {

    @Override
    public void addCentre(GymCentre centre) {
        FlipFitMockDB.gymCentres.add(centre);
        System.out.println("Gym Centre added successfully: " + centre.getCentreName());
        System.out.println("Note: Admin approval required before customers can see it.");
    }

    @Override
    public void addSlot(Slots slot) {
        FlipFitMockDB.slots.add(slot);
        System.out.println("Slot added to Centre ID: " + slot.getCentreId());
    }

    @Override
    public List<GymCentre> viewMyCentres(String ownerId) {
        List<GymCentre> myCentres = FlipFitMockDB.gymCentres.stream()
                .filter(c -> c.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());

        if (myCentres.isEmpty()) {
            System.out.println("You have no added centres.");
        } else {
            System.out.println("--- YOUR CENTRES ---");
            for (GymCentre c : myCentres) {
                System.out.println("ID: " + c.getCentreId() + " | Name: " + c.getCentreName() + " | Approved: " + c.isApproved());
            }
        }
        return myCentres;
    }
}