package com.flipfit.db.mapper;

import com.flipfit.core.Status;
import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusColumnMapper implements ColumnMapper<Status> {
    @Override
    public Status map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
        return Status.valueOf(r.getString(columnNumber));
    }
}
