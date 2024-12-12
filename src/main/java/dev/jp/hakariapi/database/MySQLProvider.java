package dev.jp.hakariapi.database;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLProvider {
    protected final HikariDataSource source = new HikariDataSource();

    public MySQLProvider(String table, String host, int port, String database, String username, String password) {
        source.setPoolName(table);
        source.setMaxLifetime(1800000L);

        source.setMaximumPoolSize(10);
        source.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        source.setUsername(username);
        source.setPassword(password);

        source.addDataSourceProperty("autoReconnect", "true");
        source.addDataSourceProperty("useSLL", "false");
        source.addDataSourceProperty("characterEncoding", "utf-8");
        source.addDataSourceProperty("encoding", "UTF-8");
        source.addDataSourceProperty("useUnicode", "true");
        source.addDataSourceProperty("rewriteBatchedStatements", "true");
        source.addDataSourceProperty("jdbcCompliantTruncation", "false");
        source.addDataSourceProperty("cachePrepStmts", "true");
        source.addDataSourceProperty("prepStmtCacheSize", "275");
        source.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    public void init() throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection == null) throw new SQLException("Failed to initialize database connection.");
            System.out.println("Database connection initialized successfully.");
        }
    }

    public void closeConnection() {
        source.close();
    }

    public Connection getConnection() {
        try {
            return source.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }

}

