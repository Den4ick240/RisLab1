package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;

public class StringTagDao extends CommittingDao<TagEntity> {

    public StringTagDao(Connection connection) {
        super(connection);
    }

    @Override
    public void insert(TagEntity obj) throws SQLException {
        String sqlStatement = TagSqlString.format(obj);
        connection.prepareStatement(sqlStatement).execute();
    }
}
