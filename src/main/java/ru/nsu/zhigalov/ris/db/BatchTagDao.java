package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchTagDao extends BatchDao<TagEntity> {
    private final Statement batch;

    public BatchTagDao(Connection connection, int batchSize, Statement batch) {
        super(connection, batch, batchSize);
        this.batch = batch;
    }

    @Override
    public void insert(TagEntity obj) throws SQLException {
        var sqlStatement = TagSqlString.format(obj);
        batch.addBatch(sqlStatement);
    }
}
