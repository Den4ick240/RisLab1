package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.Connection;
import java.sql.SQLException;

public class StringNodeDao extends CommittingDao<Node> {

    public StringNodeDao(Connection connection) {
        super(connection);
    }


    @Override
    public void insert(Node node) throws SQLException {
        String sqlStatement = NodeSqlString.format(node);
        connection.prepareStatement(sqlStatement).execute();
    }

}
