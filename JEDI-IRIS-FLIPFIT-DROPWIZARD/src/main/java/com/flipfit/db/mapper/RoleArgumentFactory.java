package com.flipfit.db.mapper;

import com.flipfit.core.Role;
import org.jdbi.v3.core.argument.Argument;
import org.jdbi.v3.core.argument.ArgumentFactory;
import org.jdbi.v3.core.config.ConfigRegistry;

import java.sql.Types;
import java.util.Optional;
import java.lang.reflect.Type;

public class RoleArgumentFactory implements ArgumentFactory {

    @Override
    public Optional<Argument> build(Type type, Object value, ConfigRegistry config) {
        if (type.equals(Role.class)) {
            return Optional.of((position, statement, ctx) -> statement.setString(position, ((Role) value).name()));
        }
        return Optional.empty();
    }
}
