package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;

public class BatchTagDao extends CommittingDao<TagEntity> {
    private final Batch batch;

    public BatchTagDao(Connection connection, Batch batch) {
        super(connection);
        this.batch = batch;
    }

    @Override
    public void insert(TagEntity obj) throws SQLException {
        var sqlStatement = TagSqlString.format(obj);
        batch.addBatch(sqlStatement);
    }

    @Override
    public void commit() throws SQLException {
        batch.executeBatch();
        super.commit();
    }
}
