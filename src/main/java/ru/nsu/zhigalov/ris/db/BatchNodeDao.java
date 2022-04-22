package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchNodeDao extends BatchDao<Node> {
    private final Statement batch;

    public BatchNodeDao(Connection connection, int batchSize, Statement batch) {
        super(connection, batch, batchSize);
        this.batch = batch;
    }

    @Override
    public void insert(Node node) throws SQLException {
        var sqlStatement = NodeSqlString.format(node);
        batch.addBatch(sqlStatement);
        super.insert(node);
    }
}
