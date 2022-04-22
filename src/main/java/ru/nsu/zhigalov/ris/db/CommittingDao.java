package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class CommittingDao<T> implements Dao<T> {
    protected final Connection connection;

    protected CommittingDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void commit() throws SQLException {
        connection.commit();
    }
}
