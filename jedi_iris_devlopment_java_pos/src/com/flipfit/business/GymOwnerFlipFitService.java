package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import java.util.ArrayList;
import java.util.List;

public class GymOwnerFlipFitService implements GymOwnerFlipFitInterface {

    @Override
    public void addCentre(GymCentre centre) {
        System.out.println("Gym Centre added: " + centre.getCentreName());
    }

    @Override
    public void addSlot(Slots slot) {
        System.out.println("Slot added to Centre: " + slot.getCentreId());
    }

    @Override
    public List<GymCentre> viewMyCentres(String ownerId) {
        System.out.println("Viewing centres for Owner: " + ownerId);
        return new ArrayList<>();
    }
}