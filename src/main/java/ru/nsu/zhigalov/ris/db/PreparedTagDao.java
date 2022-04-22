package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedTagDao extends CommittingDao<TagEntity> {
    private static final String preparedSql = "insert into tags (node_id, k, v) values (?, ?, ?)";
    private final PreparedStatement preparedStatement;

    public PreparedTagDao(Connection connection) throws SQLException {
        super(connection);
        this.preparedStatement = connection.prepareStatement(preparedSql);
    }

    @Override
    public void insert(TagEntity tagEntity) throws SQLException {
        preparedStatement.setInt(1, tagEntity.nodeId.intValue());
        preparedStatement.setString(2, tagEntity.k);
        preparedStatement.setString(3, tagEntity.v);
        preparedStatement.execute();
    }
}
