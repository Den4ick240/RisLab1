package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.Connection;
import java.sql.SQLException;

public class BatchNodeDao extends CommittingDao<Node> {
    private final Batch batch;

    public BatchNodeDao(Connection connection, Batch batch) {
        super(connection);
        this.batch = batch;
    }

    @Override
    public void insert(Node node) throws SQLException {
        var sqlStatement = NodeSqlString.format(node);
        batch.addBatch(sqlStatement);
    }

    @Override
    public void commit() throws SQLException {
        batch.executeBatch();
        super.commit();
    }
}
