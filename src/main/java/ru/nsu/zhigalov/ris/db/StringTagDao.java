package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.SQLException;

public class StringTagDao implements Dao<TagEntity> {
    private final Connection connection;
    private static final String formatString = "insert into tags (node_id, k, v) values (%s, '%s', '%s')";

    public StringTagDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(TagEntity obj) throws SQLException {
        String sql = String.format(formatString,
                obj.nodeId,
                obj.k.replace('\'', '\\'),
                obj.v.replace('\'', '\\')
                );
        connection.prepareStatement(sql).execute();
    }
}
