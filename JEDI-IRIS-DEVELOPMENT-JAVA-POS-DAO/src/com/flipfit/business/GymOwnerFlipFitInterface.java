package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.exception.ApprovalNotDoneException;
import com.flipfit.exception.RegistrationNotDoneException;
import java.util.List;

public interface GymOwnerFlipFitInterface {
    void addCentre(GymCentre centre) throws RegistrationNotDoneException;
    void addSlot(Slots slot) throws RegistrationNotDoneException;
    List<GymCentre> viewMyCentres(String ownerId);
}