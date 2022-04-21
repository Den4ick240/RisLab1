package ru.nsu.zhigalov.ris.db;

import java.sql.SQLException;

public interface Dao<T> {
    void insert(T obj) throws SQLException;
}
