package ru.nsu.zhigalov.ris.db;

import java.sql.SQLException;
import java.sql.Statement;

public class BatchTagDao implements Dao<TagEntity>{
    private final Statement batch;

    public BatchTagDao(Statement batch) {
        this.batch = batch;
    }

    @Override
    public void insert(TagEntity obj) throws SQLException {
        var sqlStatement = TagSqlString.format(obj);
        batch.addBatch(sqlStatement);
    }
}
