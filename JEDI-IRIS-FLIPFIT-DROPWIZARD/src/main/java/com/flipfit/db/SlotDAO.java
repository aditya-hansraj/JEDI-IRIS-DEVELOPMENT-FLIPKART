package com.flipfit.db;

import com.flipfit.core.Slot;
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
public interface SlotDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS slots (" +
            "id VARCHAR PRIMARY KEY, " +
            "centerId VARCHAR, " +
            "startTime TIMESTAMP, " +
            "endTime TIMESTAMP, " +
            "capacity INT, " +
            "bookedMemberIds TEXT)") // Store bookedMemberIds as a comma-separated string or JSON
    void createTable();

    @SqlUpdate("INSERT INTO slots (id, centerId, startTime, endTime, capacity, bookedMemberIds) " +
            "VALUES (:id, :centerId, :startTime, :endTime, :capacity, :bookedMemberIds)")
    @GetGeneratedKeys("id")
    String insert(@BindBean Slot slot);

    @SqlQuery("SELECT id, centerId, startTime, endTime, capacity, bookedMemberIds FROM slots WHERE id = :id")
    Optional<Slot> findById(@Bind("id") String id);

    @SqlQuery("SELECT id, centerId, startTime, endTime, capacity, bookedMemberIds FROM slots WHERE centerId = :centerId")
    List<Slot> findByCenterId(@Bind("centerId") String centerId);

    @SqlQuery("SELECT id, centerId, startTime, endTime, capacity, bookedMemberIds FROM slots")
    List<Slot> findAll();

    @SqlUpdate("UPDATE slots SET centerId = :centerId, startTime = :startTime, endTime = :endTime, " +
            "capacity = :capacity, bookedMemberIds = :bookedMemberIds WHERE id = :id")
    void update(@BindBean Slot slot);

    @SqlUpdate("DELETE FROM slots WHERE id = :id")
    void deleteById(@Bind("id") String id);
}
