package com.flipfit.db.mapper;

import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.argument.ArgumentFactory;
import org.jdbi.v3.core.config.ConfigRegistry;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;
import java.lang.reflect.Type;

public class LocalDateTimeArgumentFactory implements ArgumentFactory {

    @Override
    public Optional<Argument> build(Type type, Object value, ConfigRegistry config) {
        if (type.equals(LocalDateTime.class)) {
            return Optional.of((position, statement, ctx) -> statement.setObject(position, value, Types.TIMESTAMP));
        }
        return Optional.empty();
    }
}
