package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import java.util.List;

public interface GymOwnerFlipFitInterface {
    void addCentre(GymCentre centre);
    void addSlot(Slots slot);
    List<GymCentre> viewMyCentres(String ownerId);
}