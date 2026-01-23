package com.flipfit.db;

import com.flipfit.core.Booking;
import com.flipfit.db.mapper.LocalDateTimeArgumentFactory;
import com.flipfit.db.mapper.LocalDateTimeMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.config.RegisterArgumentFactory;
import org.jdbi.v3.sqlobject.config.RegisterColumnMapper;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;


import java.util.List;
import java.util.Optional;

@RegisterColumnMapper(LocalDateTimeMapper.class)
@RegisterArgumentFactory(LocalDateTimeArgumentFactory.class)
public interface BookingDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS bookings (" +
            "id VARCHAR PRIMARY KEY, " +
            "gymMemberId VARCHAR, " +
            "slotId VARCHAR, " +
            "bookingTime TIMESTAMP)")
    void createTable();

    @SqlUpdate("INSERT INTO bookings (id, gymMemberId, slotId, bookingTime) " +
            "VALUES (:id, :gymMemberId, :slotId, :bookingTime)")
    @GetGeneratedKeys("id")
    String insert(@BindBean Booking booking);

    @SqlQuery("SELECT id, gymMemberId, slotId, bookingTime FROM bookings WHERE id = :id")
    Optional<Booking> findById(@Bind("id") String id);

    @SqlQuery("SELECT id, gymMemberId, slotId, bookingTime FROM bookings WHERE gymMemberId = :gymMemberId")
    List<Booking> findByGymMemberId(@Bind("gymMemberId") String gymMemberId);

    @SqlQuery("SELECT id, gymMemberId, slotId, bookingTime FROM bookings WHERE slotId = :slotId")
    List<Booking> findBySlotId(@Bind("slotId") String slotId);

    @SqlQuery("SELECT id, gymMemberId, slotId, bookingTime FROM bookings")
    List<Booking> findAll();

    @SqlUpdate("UPDATE bookings SET gymMemberId = :gymMemberId, slotId = :slotId, bookingTime = :bookingTime WHERE id = :id")
    void update(@BindBean Booking booking);

    @SqlUpdate("DELETE FROM bookings WHERE id = :id")
    void deleteById(@Bind("id") String id);
}
