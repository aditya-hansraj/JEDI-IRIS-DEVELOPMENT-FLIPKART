package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.dao.GymCentreDAO;
import com.flipfit.dao.GymCentreDAOImpl;
import com.flipfit.dao.SlotDAO;
import com.flipfit.dao.SlotDAOImpl;
import com.flipfit.exception.RegistrationNotDoneException;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class GymOwnerFlipFitService.
 *
 * @author AI
 * @ClassName "GymOwnerFlipFitService"
 */
public class GymOwnerFlipFitService implements GymOwnerFlipFitInterface {
    private final GymCentreDAO gymCentreDAO = new GymCentreDAOImpl();
    private final SlotDAO slotDAO = new SlotDAOImpl();

    /**
     * Adds the centre.
     *
     * @param centre the centre
     * @throws RegistrationNotDoneException the registration not done exception
     */
    @Override
    public void addCentre(GymCentre centre) throws RegistrationNotDoneException {
        try {
            gymCentreDAO.save(centre);
        } catch (Exception ex) {
            throw new RegistrationNotDoneException("Failed to save gym centre: " + centre.getCentreName(), ex);
        }
        System.out.println("Gym Centre added successfully: " + centre.getCentreName());
        System.out.println("Note: Admin approval required before customers can see it.");
    }

    /**
     * Adds the slot.
     *
     * @param slot the slot
     * @throws RegistrationNotDoneException the registration not done exception
     */
    @Override
    public void addSlot(Slots slot) throws RegistrationNotDoneException {
        try {
            slotDAO.save(slot);
        } catch (Exception ex) {
            throw new RegistrationNotDoneException("Failed to save slot for centre: " + slot.getCentreId(), ex);
        }
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