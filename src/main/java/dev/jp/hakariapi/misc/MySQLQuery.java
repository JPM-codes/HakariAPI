package dev.jp.hakariapi.misc;

import dev.jp.hakariapi.database.MySQLProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLQuery {
    private final MySQLProvider provider;

    public MySQLQuery(MySQLProvider provider) {
        this.provider = provider;
    }

    // Método para criar tabelas
    public void createTable(String createQuery) throws SQLException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery)) {
            statement.executeUpdate();
        }
    }

    // Método para inserir registros
    public void insertRecord(String insertQuery, Object... params) throws SQLException {
        executeUpdate(insertQuery, params);
    }
    // Método para atualizar registros
    public void updateRecord(String updateQuery, Object... params) throws SQLException {
        executeUpdate(updateQuery, params);
    }
    // Método para selecionar um único registro
    public ResultSet selectRecord(String selectQuery, Object... params) throws SQLException {
        return executeQuery(selectQuery, params);
    }
    // Método para selecionar todos os registros
    public ResultSet selectAllRecords(String selectAllQuery) throws SQLException {
        return executeQuery(selectAllQuery);
    }
    // Método genérico para executar updates (INSERT, UPDATE, DELETE)
    private void executeUpdate(String query, Object... params) throws SQLException {
        try (Connection connection = provider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            setParameters(statement, params);
            statement.executeUpdate();
        }
    }

    // Método genérico para executar queries (SELECT)
    private ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection connection = provider.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        setParameters(statement, params);
        return statement.executeQuery();
    }

    // Método auxiliar para configurar os parâmetros no PreparedStatement
    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
