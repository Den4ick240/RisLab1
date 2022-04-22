package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BatchDao<T> implements Dao<T>{
    protected final Statement batch;
    protected final Connection connection;
    private int batchCounter = 0;
    private final int batchSize;

    protected BatchDao(Connection connection, Statement batch, int batchSize) {
        this.batch = batch;
        this.batchSize = batchSize;
        this.connection = connection;
    }

    @Override
    public void insert(T obj) throws SQLException {
        batchCounter++;
        if (batchCounter < batchSize) return;
        batchCounter  = 0;
        batch.executeBatch();
    }

    @Override
    public void commit() throws SQLException {
        batch.executeBatch();
        connection.commit();
    }
}
