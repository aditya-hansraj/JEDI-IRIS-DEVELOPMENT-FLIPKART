package com.flipfit.db.mapper;

import org.jdbi.v3.core.mapper.ColumnMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class LocalDateTimeMapper implements ColumnMapper<LocalDateTime> {
    @Override
    public LocalDateTime map(ResultSet r, int columnNumber, StatementContext ctx) throws SQLException {
        return r.getObject(columnNumber, LocalDateTime.class);
    }
}
