package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedNestedBatchNodeDao implements Dao<Node> {
    private final Connection connection;
    private final PreparedStatement nodeStatement;
    private final PreparedStatement tagStatement;
    private int nodeCounter = 0;
    private final int nodeBatchSize;

    public PreparedNestedBatchNodeDao(Connection connection, int batchSize) throws SQLException {
        this.connection = connection;
        nodeStatement = connection.prepareStatement(NodeSqlString.preparedSqlString);
        tagStatement = connection.prepareStatement(TagSqlString.preparedSqlString);
        nodeBatchSize = batchSize;
    }

    @Override
    public void insert(Node node) throws SQLException {
        NodeSqlString.prepare(node, nodeStatement);
        nodeStatement.addBatch();
        for (var tag : node.getTag()) {
            TagSqlString.prepare(new TagEntity(node.getId(), tag), tagStatement);
            tagStatement.addBatch();
        }

        nodeCounter++;
        if (nodeCounter >= nodeBatchSize) {
            nodeCounter = 0;
            nodeStatement.executeBatch();
            tagStatement.executeBatch();
        }
    }

    @Override
    public void commit() throws SQLException {
        if (nodeCounter > 0) {
            nodeStatement.executeBatch();
            tagStatement.executeBatch();
        }
        connection.commit();
    }
}
