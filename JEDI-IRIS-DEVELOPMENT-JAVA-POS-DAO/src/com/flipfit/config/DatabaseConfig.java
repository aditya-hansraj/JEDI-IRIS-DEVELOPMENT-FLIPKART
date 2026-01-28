package com.flipfit.config;

/**
 * Central configuration for FlipFit database connectivity.
 *
 * <p>Holds JDBC URL and credentials used by the DAO layer to
 * establish connections to the backing MySQL schema.</p>
 *
 * @author aditya-hansraj
 */
public final class DatabaseConfig {
    private DatabaseConfig() {
    }

    public static final String URL = "jdbc:mysql://localhost:3306/flipfit_schema?useSSL=false&serverTimezone=UTC";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "azax7978";
}
