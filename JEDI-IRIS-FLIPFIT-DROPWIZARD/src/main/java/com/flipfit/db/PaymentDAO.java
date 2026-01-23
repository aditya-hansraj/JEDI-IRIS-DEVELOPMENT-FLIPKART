package com.flipfit.db;

import com.flipfit.core.Payment;
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
public interface PaymentDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS payments (" +
            "id VARCHAR PRIMARY KEY, " +
            "gymMemberId VARCHAR, " +
            "slotId VARCHAR, " +
            "amount DOUBLE, " +
            "timestamp TIMESTAMP, " +
            "transactionId VARCHAR)")
    void createTable();

    @SqlUpdate("INSERT INTO payments (id, gymMemberId, slotId, amount, timestamp, transactionId) " +
            "VALUES (:id, :gymMemberId, :slotId, :amount, :timestamp, :transactionId)")
    @GetGeneratedKeys("id")
    String insert(@BindBean Payment payment);

    @SqlQuery("SELECT id, gymMemberId, slotId, amount, timestamp, transactionId FROM payments WHERE id = :id")
    Optional<Payment> findById(@Bind("id") String id);

    @SqlQuery("SELECT id, gymMemberId, slotId, amount, timestamp, transactionId FROM payments WHERE gymMemberId = :gymMemberId")
    List<Payment> findByGymMemberId(@Bind("gymMemberId") String gymMemberId);

    @SqlQuery("SELECT id, gymMemberId, slotId, amount, timestamp, transactionId FROM payments WHERE slotId = :slotId")
    List<Payment> findBySlotId(@Bind("slotId") String slotId);

    @SqlQuery("SELECT id, gymMemberId, slotId, amount, timestamp, transactionId FROM payments")
    List<Payment> findAll();

    @SqlUpdate("UPDATE payments SET gymMemberId = :gymMemberId, slotId = :slotId, amount = :amount, " +
            "timestamp = :timestamp, transactionId = :transactionId WHERE id = :id")
    void update(@BindBean Payment payment);

    @SqlUpdate("DELETE FROM payments WHERE id = :id")
    void deleteById(@Bind("id") String id);
}
