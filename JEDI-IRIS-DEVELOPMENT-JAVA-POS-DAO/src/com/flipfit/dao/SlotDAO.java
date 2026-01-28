package com.flipfit.dao;

import com.flipfit.bean.Slots;

public interface SlotDAO {
    void save(Slots slot);
    Slots findById(String slotId);
    boolean reserveSeat(String slotId);
    void releaseSeat(String slotId);
    java.util.List<Slots> findByCentre(String centreId);
}
