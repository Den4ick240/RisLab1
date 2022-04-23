package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.*;

public class PreparedNodeDao extends CommittingDao<Node> {

    private final PreparedStatement preparedStatement;

    public PreparedNodeDao(Connection connection) throws SQLException {
        super(connection);
        preparedStatement = connection.prepareStatement(NodeSqlString.preparedSqlString);
    }

    @Override
    public void insert(Node node) throws SQLException {
        NodeSqlString.prepare(node, preparedStatement);
        preparedStatement.execute();
    }
}
