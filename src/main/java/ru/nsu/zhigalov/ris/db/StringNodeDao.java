package ru.nsu.zhigalov.ris.db;

import generated.Node;
import generated.Tag;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StringNodeDao extends AbstractNestedNodeDao {
    private final Connection connection;

    public StringNodeDao(Connection connection, Dao<TagEntity> tagEntityDao) {
        super(tagEntityDao);
        this.connection = connection;
    }


    @Override
    protected void insertNode(Node node) throws SQLException {
        String sqlStatement = NodeSqlString.format(node);
        connection.prepareStatement(sqlStatement).execute();
        connection.commit();
    }
}
