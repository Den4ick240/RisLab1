package ru.nsu.zhigalov.ris.db;

import generated.Node;

import java.sql.SQLException;
import java.sql.Statement;

public class BatchNodeDao extends AbstractNestedNodeDao {
    private final Statement batch;

    public BatchNodeDao(Statement batch, Dao<TagEntity> tagEntityDao) {
        super(tagEntityDao);
        this.batch = batch;
    }

    @Override
    protected void insertNode(Node node) throws SQLException {
        var sqlStatement = NodeSqlString.format(node);
        batch.addBatch(sqlStatement);
    }
}
