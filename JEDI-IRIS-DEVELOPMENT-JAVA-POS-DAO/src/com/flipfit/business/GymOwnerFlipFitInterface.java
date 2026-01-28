package com.flipfit.business;

import com.flipfit.bean.GymCentre;
import com.flipfit.bean.Slots;
import com.flipfit.exception.ApprovalNotDoneException;
import com.flipfit.exception.RegistrationNotDoneException;
import java.util.List;

/**
 * Contract for FlipFit operations available to a gym owner.
 *
 * <p>Implementations of this interface allow owners to register
 * gym centres, manage slots, and view their own centres.</p>
 *
 * @author aditya-hansraj
 */
public interface GymOwnerFlipFitInterface {

    /**
     * Registers a new gym centre for the owner.
     *
     * @param centre the centre details to register
     * @throws RegistrationNotDoneException if the centre could not be registered
     */
    void addCentre(GymCentre centre) throws RegistrationNotDoneException;

    /**
     * Adds a new slot to an existing gym centre.
     *
     * @param slot the slot to add
     * @throws RegistrationNotDoneException if the slot could not be registered
     */
    void addSlot(Slots slot) throws RegistrationNotDoneException;

    /**
     * Retrieves all centres owned by the specified owner.
     *
     * @param ownerId the unique identifier of the owner
     * @return list of centres belonging to the owner
     */
    List<GymCentre> viewMyCentres(String ownerId);
}