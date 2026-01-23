package com.flipfit.db;

import com.flipfit.core.Center;
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
public interface CenterDAO {

    @SqlUpdate("CREATE TABLE IF NOT EXISTS centers (" +
            "id VARCHAR PRIMARY KEY, " +
            "name VARCHAR, " +
            "address VARCHAR, " +
            "gymOwnerId VARCHAR, " +
            "approvalStatus VARCHAR)")
    void createTable();

    @SqlUpdate("INSERT INTO centers (id, name, address, gymOwnerId, approvalStatus) " +
            "VALUES (:id, :name, :address, :gymOwnerId, :approvalStatus)")
    @GetGeneratedKeys("id")
    String insert(@BindBean Center center);

    @SqlQuery("SELECT id, name, address, gymOwnerId, approvalStatus FROM centers WHERE id = :id")
    Optional<Center> findById(@Bind("id") String id);

    @SqlQuery("SELECT id, name, address, gymOwnerId, approvalStatus FROM centers WHERE gymOwnerId = :gymOwnerId")
    List<Center> findByGymOwnerId(@Bind("gymOwnerId") String gymOwnerId);

    @SqlQuery("SELECT id, name, address, gymOwnerId, approvalStatus FROM centers")
    List<Center> findAll();

    @SqlUpdate("UPDATE centers SET name = :name, address = :address, gymOwnerId = :gymOwnerId, approvalStatus = :approvalStatus WHERE id = :id")
    void update(@BindBean Center center);

    @SqlUpdate("DELETE FROM centers WHERE id = :id")
    void deleteById(@Bind("id") String id);
}
