package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedTagDao implements Dao<TagEntity> {
    private static final String preparedSql = "insert into tags (node_id, k, v) values (?, ?, ?)";
    private final Connection connection;
    private final PreparedStatement preparedStatement;

    public PreparedTagDao(Connection connection) throws SQLException {
        this.connection = connection;
        this.preparedStatement = connection.prepareStatement(preparedSql);
    }

    @Override
    public void insert(TagEntity tagEntity) throws SQLException {
        preparedStatement.setInt(1, tagEntity.nodeId.intValue());
        preparedStatement.setString(2, tagEntity.k);
        preparedStatement.setString(3, tagEntity.v);
        preparedStatement.execute();
        connection.commit();
    }
}