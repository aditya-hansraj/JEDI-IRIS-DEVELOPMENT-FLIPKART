package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.dao.GymCentreDAO;
import com.flipfit.dao.GymCentreDAOImpl;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.SlotDAOImpl;
import java.util.List;

public class GymOwnerFlipFitService implements GymOwnerFlipFitInterface {
    private final GymCentreDAO gymCentreDAO = new GymCentreDAOImpl();
    private final SlotDAO slotDAO = new SlotDAOImpl();

    @Override
    public void addCentre(GymCentre centre) {
        gymCentreDAO.save(centre);
        System.out.println("Gym Centre added successfully: " + centre.getCentreName());
        System.out.println("Note: Admin approval required before customers can see it.");
    }

    @Override
    public void addSlot(Slots slot) {
        slotDAO.save(slot);
        System.out.println("Slot added to Centre ID: " + slot.getCentreId());
    }

    @Override
    public List<GymCentre> viewMyCentres(String ownerId) {
        List<GymCentre> myCentres = gymCentreDAO.findByOwner(ownerId);
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