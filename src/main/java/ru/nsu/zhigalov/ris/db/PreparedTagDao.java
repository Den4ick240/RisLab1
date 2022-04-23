package ru.nsu.zhigalov.ris.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PreparedTagDao extends CommittingDao<TagEntity> {
    private final PreparedStatement preparedStatement;

    public PreparedTagDao(Connection connection) throws SQLException {
        super(connection);
        this.preparedStatement = connection.prepareStatement(TagSqlString.preparedSqlString);
    }

    @Override
    public void insert(TagEntity tagEntity) throws SQLException {
        TagSqlString.prepare(tagEntity, preparedStatement);
        preparedStatement.execute();
    }
}
