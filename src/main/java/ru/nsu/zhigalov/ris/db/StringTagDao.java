package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;

public class StringTagDao implements Dao<TagEntity> {
    private final Connection connection;

    public StringTagDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(TagEntity obj) throws SQLException {
        String sqlStatement = TagSqlString.format(obj);
        connection.prepareStatement(sqlStatement).execute();
        connection.commit();
    }
}
