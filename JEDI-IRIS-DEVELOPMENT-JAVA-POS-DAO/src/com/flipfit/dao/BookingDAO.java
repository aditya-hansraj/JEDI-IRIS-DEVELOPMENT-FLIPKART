package com.flipfit.dao;

import com.flipfit.bean.Booking;
import java.util.List;

public interface BookingDAO {
    void save(Booking booking);
    boolean delete(String bookingId);
    List<Booking> findByUser(String userId);
}
