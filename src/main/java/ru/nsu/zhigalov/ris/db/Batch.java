package ru.nsu.zhigalov.ris.db;

import java.sql.SQLException;
import java.sql.Statement;

public class Batch {
    private final Statement batch;
    private final int batchSize;
    private int batchCounter = 0;

    public Batch(Statement batch, int batchSize) {
        this.batch = batch;
        this.batchSize = batchSize;
    }

    public void executeBatch() throws SQLException {
        if (batchCounter == 0) return;
        batch.executeBatch();
        batchCounter = 0;
    }

    public void addBatch(String sql) throws SQLException {
        batch.addBatch(sql);
        batchCounter++;
        if (batchCounter >= batchSize) executeBatch();
    }
}
