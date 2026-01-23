package com.flipfit.db.mapper;

import com.flipfit.core.Role;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleColumnMapper implements ColumnMapper<Role> {
    @Override
    public Role map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
        return Role.valueOf(r.getString(columnNumber));
    }
}
