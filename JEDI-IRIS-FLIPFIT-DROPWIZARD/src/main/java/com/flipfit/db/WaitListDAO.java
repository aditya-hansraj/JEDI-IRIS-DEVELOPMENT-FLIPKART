package com.flipfit.db;

import com.flipfit.core.WaitList;
import com.flipfit.db.mapper.StatusArgumentFactory;
import com.flipfit.db.mapper.StatusColumnMapper;
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

@RegisterColumnMapper(StatusColumnMapper.class)
@RegisterArgumentFactory(StatusArgumentFactory.class)
public interface WaitListDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS waitlists (" +
            "id VARCHAR PRIMARY KEY, " +
            "gymMemberId VARCHAR, " +
            "slotId VARCHAR, " +
            "rank INT, " +
            "status VARCHAR)")
    void createTable();

    @SqlUpdate("INSERT INTO waitlists (id, gymMemberId, slotId, rank, status) " +
            "VALUES (:id, :gymMemberId, :slotId, :rank, :status)")
    @GetGeneratedKeys("id")
    String insert(@BindBean WaitList waitList);

    @SqlQuery("SELECT id, gymMemberId, slotId, rank, status FROM waitlists WHERE id = :id")
    Optional<WaitList> findById(@Bind("id") String id);

    @SqlQuery("SELECT id, gymMemberId, slotId, rank, status FROM waitlists WHERE slotId = :slotId ORDER BY rank ASC")
    List<WaitList> findBySlotId(@Bind("slotId") String slotId);

    @SqlQuery("SELECT id, gymMemberId, slotId, rank, status FROM waitlists WHERE gymMemberId = :gymMemberId")
    List<WaitList> findByGymMemberId(@Bind("gymMemberId") String gymMemberId);

    @SqlQuery("SELECT id, gymMemberId, slotId, rank, status FROM waitlists")
    List<WaitList> findAll();

    @SqlUpdate("UPDATE waitlists SET gymMemberId = :gymMemberId, slotId = :slotId, rank = :rank, status = :status WHERE id = :id")
    void update(@BindBean WaitList waitList);

    @SqlUpdate("DELETE FROM waitlists WHERE id = :id")
    void deleteById(@Bind("id") String id);
}
